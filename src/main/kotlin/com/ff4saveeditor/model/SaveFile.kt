package com.ff4saveeditor.model

import com.ff4saveeditor.app.InventoryEvent
import com.ff4saveeditor.app.InventoryRequest
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption
import java.util.*

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
    var file: File? = null

    init {
        saveFile.loaded.value = false
        subscribe<InventoryRequest> {
            val inventory = saveFile.currentSlot.value.saveSlot.inventory.value
            fire(InventoryEvent(inventory))
        }
    }

    fun loadSave(f: File) {
        file = f
        val reader = FileChannel.open(file!!.toPath(), StandardOpenOption.READ)
        var slotOffset = 0L

//      Read data from file into each save slot, adjust slotOffset to next "cd1000" string each iteration
        saveSlots.forEach {
            it.clear()

            val gilBuffer = reader.readData(slotOffset + Offsets.GIL, 4)
            it.saveSlot.gil.value = gilBuffer.int

            val timeBuffer = reader.readData(slotOffset + Offsets.TIME, 4)
            val totalSeconds = timeBuffer.int
            it.saveSlot.hours.value = totalSeconds / 3600
            it.saveSlot.minutes.value = totalSeconds % 3600 / 60
            it.saveSlot.seconds.value = totalSeconds % 3600 % 60

//          Read inventory
            val itemCountBuffer = reader.readData(slotOffset + Offsets.ITEM_COUNT, 4)
            val itemCount = itemCountBuffer.int
            it.saveSlot.itemCount.value = itemCount

            var itemOffset = Offsets.FIRST_ITEM
            var quantityOffset = Offsets.FIRST_QUANTITY

            for (i in 1..itemCount) {
                val itemBuffer = reader.readData(slotOffset + itemOffset, 2)
                val quantityBuffer = reader.readData(slotOffset + quantityOffset, 2)
                val item = itemBuffer.short.toInt()
                val quantity = quantityBuffer.short.toInt()

                it.saveSlot.inventory.value.add(InventoryEntry(item, quantity))
                itemOffset += Offsets.ITEM_SEPARATION
                quantityOffset += Offsets.ITEM_SEPARATION
            }

//          Read bestiary
            for (monsterOffset in Monsters.monsterMap.keys) {
                val monsterBuffer = reader.readData(slotOffset + monsterOffset, 2)
                val monsterValue = monsterBuffer.short.toInt()
                val numSlain = (monsterValue and 0xFFF0)/16
                val isNew = (monsterValue and 0x0002) shr 1 == 1
                val isSeen = (monsterValue and 0x0001) == 1
                it.saveSlot.bestiary.value.add(BestiaryEntry(Monsters.monsterMap[monsterOffset], isSeen, isNew, numSlain))
            }

//          Read character data
            var charOffset = Offsets.FIRST_CHAR
            it.saveSlot.characterControllers.value.forEach {
                val levelBuffer = reader.readData(slotOffset + charOffset, 1)
                it.character.level.value = levelBuffer.get().toInt()

                val currHPBuffer = reader.readData(slotOffset + charOffset + Offsets.CURR_HP, 4)
                it.character.currHP.value = currHPBuffer.int

                val maxHPBuffer = reader.readData(slotOffset + charOffset + Offsets.MAX_HP, 4)
                it.character.maxHP.value = maxHPBuffer.int

                val currMPBuffer = reader.readData(slotOffset + charOffset + Offsets.CURR_MP, 4)
                it.character.currMP.value = currMPBuffer.int

                val maxMPBuffer = reader.readData(slotOffset + charOffset + Offsets.MAX_MP, 4)
                it.character.maxMP.value = maxMPBuffer.int

                val strengthBuffer = reader.readData(slotOffset + charOffset + Offsets.STRENGTH, 1)
                it.character.strength.value = strengthBuffer.get().toInt()

                val staminaBuffer = reader.readData(slotOffset + charOffset + Offsets.STAMINA, 1)
                it.character.stamina.value = staminaBuffer.get().toInt()

                val speedBuffer = reader.readData(slotOffset + charOffset + Offsets.SPEED, 1)
                it.character.speed.value = speedBuffer.get().toInt()

                val intellectBuffer = reader.readData(slotOffset + charOffset + Offsets.INTELLECT, 1)
                it.character.intellect.value = intellectBuffer.get().toInt()

                val spiritBuffer = reader.readData(slotOffset + charOffset + Offsets.SPIRIT, 1)
                it.character.spirit.value = spiritBuffer.get().toInt()

                val rightHandBuffer = reader.readData(slotOffset + charOffset + Offsets.RIGHT_HAND, 2)
                it.character.rightHand.value = Equipment.handMap[rightHandBuffer.short.toInt()] ?: "Empty"

                val leftHandBuffer = reader.readData(slotOffset + charOffset + Offsets.LEFT_HAND, 2)
                it.character.leftHand.value = Equipment.handMap[leftHandBuffer.short.toInt()] ?: "Empty"

                val headBuffer = reader.readData(slotOffset + charOffset + Offsets.HEAD, 2)
                it.character.head.value = Equipment.headMap[headBuffer.short.toInt()] ?: "Empty"

                val bodyBuffer = reader.readData(slotOffset + charOffset + Offsets.BODY, 2)
                it.character.body.value = Equipment.bodyMap[bodyBuffer.short.toInt()] ?: "Empty"

                val armBuffer = reader.readData(slotOffset + charOffset + Offsets.ARM, 2)
                it.character.arm.value = Equipment.armMap[armBuffer.short.toInt()] ?: "Empty"

                charOffset += Offsets.CHAR_SEPARATION
            }

            slotOffset += Offsets.SLOT_SEPARATION
        }
        saveFile.loaded.value = true
        chooseSlot(0)
    }

    fun writeSave():Int {
//      Make backup save
        var backup = File(file?.toPath().toString() + ".BAK")
        var backupNum = 0
        while (backup.exists()) {
            backupNum++
            backup = File(file?.toPath().toString() + ".BAK" + backupNum)
        }
        file?.copyTo(backup)

        val writer = FileChannel.open(file?.toPath(), StandardOpenOption.WRITE)
        var slotOffset = 0L

//      Write from save slot models into save file, adjust slotOffset to next "cd1000" string each iteration
        saveSlots.forEach {
            writer.writeInt(slotOffset + Offsets.GIL, it.saveSlot.gil.value.toInt())

            val totalSeconds =
                it.saveSlot.hours.value.toInt() * 3600 + it.saveSlot.minutes.value.toInt() * 60 + it.saveSlot.seconds.value.toInt()
            writer.writeInt(slotOffset + Offsets.TIME, totalSeconds)

//          Write inventory
            writer.writeInt(slotOffset + Offsets.ITEM_COUNT, it.saveSlot.inventory.value.size)

            var itemOffset = Offsets.FIRST_ITEM
            var quantityOffset = Offsets.FIRST_QUANTITY

            for (i in 0 until it.saveSlot.inventory.value.size) {
                writer.writeShort(slotOffset + itemOffset, it.saveSlot.inventory.value[i].id)
                writer.writeShort(slotOffset + quantityOffset, it.saveSlot.inventory.value[i].quantity)

                itemOffset += Offsets.ITEM_SEPARATION
                quantityOffset += Offsets.ITEM_SEPARATION
            }

//          Write bestiary
            for ((bestiaryIterator, monsterOffset) in Monsters.monsterMap.keys.withIndex()) {
                val currentMonster = it.saveSlot.bestiary.value[bestiaryIterator]
                var monsterValue = 0
                if (currentMonster.seen)
                    monsterValue += 0x0001
                if (currentMonster.isNew)
                    monsterValue += 0x0002
                monsterValue += currentMonster.numSlain * 16
                writer.writeShort(slotOffset + monsterOffset, monsterValue)
            }

//          Write character data
            var charOffset = Offsets.FIRST_CHAR
            it.saveSlot.characterControllers.value.forEach {
                writer.writeByte(slotOffset + charOffset, it.character.level.value.toInt())

                writer.writeInt(slotOffset + charOffset + Offsets.CURR_HP, it.character.currHP.value.toInt())

                writer.writeInt(slotOffset + charOffset + Offsets.MAX_HP, it.character.maxHP.value.toInt())

                writer.writeInt(slotOffset + charOffset + Offsets.CURR_MP, it.character.currMP.value.toInt())

                writer.writeInt(slotOffset + charOffset + Offsets.MAX_MP, it.character.maxMP.value.toInt())

                writer.writeByte(slotOffset + charOffset + Offsets.STRENGTH, it.character.strength.value.toInt())

                writer.writeByte(slotOffset + charOffset + Offsets.STAMINA, it.character.stamina.value.toInt())

                writer.writeByte(slotOffset + charOffset + Offsets.SPEED, it.character.speed.value.toInt())

                writer.writeByte(slotOffset + charOffset + Offsets.INTELLECT, it.character.intellect.value.toInt())

                writer.writeByte(slotOffset + charOffset + Offsets.SPIRIT, it.character.spirit.value.toInt())

                writer.writeShort(slotOffset + charOffset + Offsets.RIGHT_HAND, Items.inverseUniversal[it.character.rightHand.value] ?: 0xFF9D)

                writer.writeShort(slotOffset + charOffset + Offsets.LEFT_HAND, Items.inverseUniversal[it.character.leftHand.value] ?: 0xFF9D)

                writer.writeShort(slotOffset + charOffset + Offsets.HEAD, Items.inverseUniversal[it.character.head.value] ?: 0xFF9D)

                writer.writeShort(slotOffset + charOffset + Offsets.BODY, Items.inverseUniversal[it.character.body.value] ?: 0xFF9D)

                writer.writeShort(slotOffset + charOffset + Offsets.ARM, Items.inverseUniversal[it.character.arm.value] ?: 0xFF9D)

                charOffset += Offsets.CHAR_SEPARATION
            }

            slotOffset += Offsets.SLOT_SEPARATION
        }
        return backupNum
    }

    fun chooseSlot(index: Int) {
        saveSlots[index].select()
        for (i in 0..2) {
            if (i != index) saveSlots[i].deselect()
        }
        saveFile.currentSlot.value = saveSlots[index]
        fire(InventoryRequest())
    }

//  Reads data of specified size from save file at specified offset
    private fun FileChannel.readData(pos: Long, size: Int): ByteBuffer {
        position(pos)
        val dataBuffer = ByteBuffer.allocate(size)
        dataBuffer.order(ByteOrder.LITTLE_ENDIAN)
        do {
            read(dataBuffer)
        } while(dataBuffer.hasRemaining())
        dataBuffer.flip()
        return dataBuffer
    }

//  Functions to write data to file
    private fun FileChannel.writeInt(pos: Long, data: Int) {
        position(pos)
        val dataBuffer = ByteBuffer.allocate(4)
        dataBuffer.order(ByteOrder.LITTLE_ENDIAN)
        dataBuffer.putInt(data)
        dataBuffer.rewind()
        write(dataBuffer)
    }

    private fun FileChannel.writeShort(pos: Long, data: Int) {
        position(pos)
        val dataBuffer = ByteBuffer.allocate(2)
        dataBuffer.order(ByteOrder.LITTLE_ENDIAN)
        dataBuffer.putInt(data)
        dataBuffer.rewind()
        write(dataBuffer)
    }

    private fun FileChannel.writeByte(pos: Long, data: Int) {
        position(pos)
        val dataBuffer = ByteBuffer.allocate(1)
        dataBuffer.order(ByteOrder.LITTLE_ENDIAN)
        dataBuffer.putInt(data)
        dataBuffer.rewind()
        write(dataBuffer)
    }
}