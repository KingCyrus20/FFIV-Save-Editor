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
            println(it.saveSlot.gil)

            val timeBuffer = readData(slotOffset + 0x2304, 4)
            val totalSeconds = timeBuffer.int
            it.saveSlot.hours.value = totalSeconds / 3600
            it.saveSlot.minutes.value = totalSeconds % 3600 / 60
            it.saveSlot.seconds.value = totalSeconds % 3600 % 60

            slotOffset += 0x3DC0
//          TODO: Character loop
            var charOffset = 0
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