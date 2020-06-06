package com.ff4saveeditor.model

import javafx.beans.property.SimpleBooleanProperty
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
    var loaded by loadedProperty

    init {
        for(i in 1..3) saveSlotControllers.add(SaveSlotController())
    }
}

class SaveFileModel(saveFile: SaveFile) : ItemViewModel<SaveFile>(saveFile){
    val loaded = bind(SaveFile::loadedProperty)
    val saveSlotControllers = bind(SaveFile::saveSlotControllers)
}

class SaveFileScope: Scope() {
    val saveFile = SaveFileModel(SaveFile())
}

class SaveFileController: Controller() {
    private val saveFileScope = SaveFileScope()
    val saveFile = saveFileScope.saveFile

    init {
        saveFile.loaded.value = false
    }

    fun loadSave(f: File) {
        val reader = FileChannel.open(f.toPath(), StandardOpenOption.READ)
        val saveSlots = saveFile.saveSlotControllers.value
        var slotOffset = 0
//      Read data from file into each save slot, TODO: adjust slotOffset to next "cd1000" string each time
        saveSlots.forEach() {
            reader.position(0x88)
            val gilBuffer = ByteBuffer.allocate(4)
            gilBuffer.order(ByteOrder.LITTLE_ENDIAN)
            do {
                reader.read(gilBuffer)
            } while(gilBuffer.hasRemaining())
            gilBuffer.flip()
            it.saveSlot.gil.value = gilBuffer.int

//          TODO: Character loop
            var charOffset = 0
        }
        saveFile.loaded.value = true
        saveSlots[0].select()
        saveSlots[1].deselect()
        saveSlots[2].deselect()
    }
}