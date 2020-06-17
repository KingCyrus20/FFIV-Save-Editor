package com.ff4saveeditor.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption

class SaveFile() {
    val saveSlotControllers: ObservableList<SaveSlotController> = FXCollections.observableArrayList<SaveSlotController>()
    val loadedProperty = SimpleBooleanProperty()

    init {
        for(i in 1..3) saveSlotControllers.add(SaveSlotController())
    }

    val currentSlotProperty: SimpleObjectProperty<SaveSlotController> = SimpleObjectProperty<SaveSlotController>(saveSlotControllers[0])
}

class SaveFileModel(saveFile: SaveFile) : ItemViewModel<SaveFile>(saveFile){
    val saveSlotControllers = bind(SaveFile::saveSlotControllers)
    val loaded = bind(SaveFile::loadedProperty)
    val currentSlot = bind(SaveFile::currentSlotProperty)
}

class SaveFileScope: Scope() {
    val saveFile = SaveFileModel(SaveFile())
}

class SaveFileController: Controller() {
    private val saveFileScope = SaveFileScope()
    val saveFile = saveFileScope.saveFile
    private val saveSlots: ObservableList<SaveSlotController> = saveFile.saveSlotControllers.value
    var reader: FileChannel? = null

    init {
        saveFile.loaded.value = false
    }

    fun loadSave(f: File) {
        reader = FileChannel.open(f.toPath(), StandardOpenOption.READ)
        var slotOffset = 0L
//      Read data from file into each save slot, adjust slotOffset to next "cd1000" string each iteration
        saveSlots.forEach() {
            val gilBuffer = readData(slotOffset + 0x88, 4)
            it.saveSlot.gil.value = gilBuffer.int

            val timeBuffer = readData(slotOffset + 0x2304, 4)
            val totalSeconds = timeBuffer.int
            it.saveSlot.hours.value = totalSeconds / 3600
            it.saveSlot.minutes.value = totalSeconds % 3600 / 60
            it.saveSlot.seconds.value = totalSeconds % 3600 % 60

//          TODO: Character loop
            var charOffset = 0x9C
            it.saveSlot.characterControllers.value.forEach() {
                val levelBuffer = readData(slotOffset + charOffset, 1)
                it.character.level.value = levelBuffer.get().toInt()

                val currHPBuffer = readData(slotOffset + charOffset + 0xC, 4)
                it.character.currHP.value = currHPBuffer.int

                val maxHPBuffer = readData(slotOffset + charOffset + 0x10, 4)
                it.character.maxHP.value = maxHPBuffer.int

                val currMPBuffer = readData(slotOffset + charOffset + 0x14, 4)
                it.character.currMP.value = currMPBuffer.int

                val maxMPBuffer = readData(slotOffset + charOffset + 0x18, 4)
                it.character.maxMP.value = maxMPBuffer.int

                val strengthBuffer = readData(slotOffset + charOffset + 0x1CA, 1)
                it.character.strength.value = strengthBuffer.get().toInt()

                val staminaBuffer = readData(slotOffset + charOffset + 0x1CB, 1)
                it.character.stamina.value = staminaBuffer.get().toInt()

                val speedBuffer = readData(slotOffset + charOffset + 0x1CC, 1)
                it.character.speed.value = speedBuffer.get().toInt()

                val intellectBuffer = readData(slotOffset + charOffset + 0x1CD, 1)
                it.character.intellect.value = intellectBuffer.get().toInt()

                val spiritBuffer = readData(slotOffset + charOffset + 0x1CE, 1)
                it.character.spirit.value = spiritBuffer.get().toInt()

                charOffset += 0x1D4
            }

            slotOffset += 0x3DC0
        }
        saveFile.loaded.value = true
        chooseSlot(0)
    }

    fun chooseSlot(index: Int) {
        saveSlots[index].select()
        for (i in 0..2) {
            if (i != index) saveSlots[i].deselect()
        }
        saveFile.currentSlot.value = saveSlots[index]
    }

    private fun readData(position: Long, size: Int): ByteBuffer {
        reader?.position(position)
        val dataBuffer = ByteBuffer.allocate(size)
        dataBuffer.order(ByteOrder.LITTLE_ENDIAN)
        do {
            reader?.read(dataBuffer)
        } while(dataBuffer.hasRemaining())
        dataBuffer.flip()
        return dataBuffer
    }
}