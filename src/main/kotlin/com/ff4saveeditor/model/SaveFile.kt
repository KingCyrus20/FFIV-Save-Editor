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

class SaveFile {
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
        saveSlots.forEach {
            val gilBuffer = readData(slotOffset + Offsets.GIL, 4)
            it.saveSlot.gil.value = gilBuffer.int

            val timeBuffer = readData(slotOffset + Offsets.TIME, 4)
            val totalSeconds = timeBuffer.int
            it.saveSlot.hours.value = totalSeconds / 3600
            it.saveSlot.minutes.value = totalSeconds % 3600 / 60
            it.saveSlot.seconds.value = totalSeconds % 3600 % 60

//          Read inventory
            val itemCountBuffer = readData(slotOffset + Offsets.ITEM_COUNT, 4)
            val itemCount = itemCountBuffer.int
            it.saveSlot.itemCount.value = itemCount

            var itemOffset = Offsets.FIRST_ITEM
            var quantityOffset = Offsets.FIRST_QUANTITY

            for (i in 1..itemCount) {
                val itemBuffer = readData(slotOffset + itemOffset, 2)
                val quantityBuffer = readData(slotOffset + quantityOffset, 2)
                val item = itemBuffer.short.toInt()
                val quantity = quantityBuffer.short.toInt()

                it.saveSlot.inventory.value.add(InventoryEntry(item, quantity))
                itemOffset += Offsets.ITEM_SEPARATION
                quantityOffset += Offsets.ITEM_SEPARATION
            }

//          Read character data
            var charOffset = Offsets.FIRST_CHAR
            it.saveSlot.characterControllers.value.forEach {
                val levelBuffer = readData(slotOffset + charOffset, 1)
                it.character.level.value = levelBuffer.get().toInt()

                val currHPBuffer = readData(slotOffset + charOffset + Offsets.CURR_HP, 4)
                it.character.currHP.value = currHPBuffer.int

                val maxHPBuffer = readData(slotOffset + charOffset + Offsets.MAX_HP, 4)
                it.character.maxHP.value = maxHPBuffer.int

                val currMPBuffer = readData(slotOffset + charOffset + Offsets.CURR_MP, 4)
                it.character.currMP.value = currMPBuffer.int

                val maxMPBuffer = readData(slotOffset + charOffset + Offsets.MAX_MP, 4)
                it.character.maxMP.value = maxMPBuffer.int

                val strengthBuffer = readData(slotOffset + charOffset + Offsets.STRENGTH, 1)
                it.character.strength.value = strengthBuffer.get().toInt()

                val staminaBuffer = readData(slotOffset + charOffset + Offsets.STAMINA, 1)
                it.character.stamina.value = staminaBuffer.get().toInt()

                val speedBuffer = readData(slotOffset + charOffset + Offsets.SPEED, 1)
                it.character.speed.value = speedBuffer.get().toInt()

                val intellectBuffer = readData(slotOffset + charOffset + Offsets.INTELLECT, 1)
                it.character.intellect.value = intellectBuffer.get().toInt()

                val spiritBuffer = readData(slotOffset + charOffset + Offsets.SPIRIT, 1)
                it.character.spirit.value = spiritBuffer.get().toInt()

                val rightHandBuffer = readData(slotOffset + charOffset + Offsets.RIGHT_HAND, 2)
                when (val rightHandString = Equipment.handMap[rightHandBuffer.short.toInt()]) {
                    null -> it.character.rightHand.value = "Empty"
                    else -> it.character.rightHand.value = rightHandString
                }

                val leftHandBuffer = readData(slotOffset + charOffset + Offsets.LEFT_HAND, 2)
                when (val leftHandString = Equipment.handMap[leftHandBuffer.short.toInt()]) {
                    null -> it.character.leftHand.value = "Empty"
                    else -> it.character.leftHand.value = leftHandString
                }

                val headBuffer = readData(slotOffset + charOffset + Offsets.HEAD, 2)
                when (val headString = Equipment.headMap[headBuffer.short.toInt()]) {
                    null -> it.character.head.value = "Empty"
                    else -> it.character.head.value = headString
                }

                val bodyBuffer = readData(slotOffset + charOffset + Offsets.BODY, 2)
                when (val bodyString = Equipment.bodyMap[bodyBuffer.short.toInt()]) {
                    null -> it.character.body.value = "Empty"
                    else -> it.character.body.value = bodyString
                }

                val armBuffer = readData(slotOffset + charOffset + Offsets.ARM, 2)
                when (val armString = Equipment.armMap[armBuffer.short.toInt()]) {
                    null -> it.character.arm.value = "Empty"
                    else -> it.character.arm.value = armString
                }

                charOffset += Offsets.CHAR_SEPARATION
            }

            slotOffset += Offsets.SLOT_SEPARATION
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

    //Reads data of specified size from save file at specified offset
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