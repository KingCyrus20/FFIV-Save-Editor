package com.ff4saveeditor.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class SaveSlot() {
    val gilProperty = SimpleIntegerProperty()
    var gil by gilProperty
    val selectedProperty = SimpleBooleanProperty()
    var selected by selectedProperty
}

class SaveSlotModel(saveSlot: SaveSlot) : ItemViewModel<SaveSlot>(saveSlot) {
    val gil = bind(SaveSlot::gilProperty)
    val selected = bind(SaveSlot::selectedProperty)
}

class SaveSlotScope: Scope() {
    val saveSlot = SaveSlotModel(SaveSlot())
}

class SaveSlotController: Controller() {
    private val saveSlotScope = SaveSlotScope()
    val saveSlot = saveSlotScope.saveSlot

    init {
        saveSlot.gil.value = 0
        saveSlot.selected.value = false
    }

    fun select() {
        saveSlot.selected.value = true
    }

    fun deselect() {
        saveSlot.selected.value = false
    }
}
