package com.ff4saveeditor.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*

class SaveSlot() {
    val gilProperty = SimpleIntegerProperty()
    val selectedProperty = SimpleBooleanProperty()
    val hoursProperty = SimpleIntegerProperty()
    val minutesProperty = SimpleIntegerProperty()
    val secondsProperty = SimpleIntegerProperty()
}

class SaveSlotModel(saveSlot: SaveSlot) : ItemViewModel<SaveSlot>(saveSlot) {
    val gil = bind(SaveSlot::gilProperty)
    val selected = bind(SaveSlot::selectedProperty)
    val hours = bind(SaveSlot::hoursProperty)
    val minutes = bind(SaveSlot::minutesProperty)
    val seconds = bind(SaveSlot::secondsProperty)
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
        saveSlot.hours.value = 0
        saveSlot.minutes.value = 0
        saveSlot.seconds.value = 0
    }

    fun select() {
        saveSlot.selected.value = true
    }

    fun deselect() {
        saveSlot.selected.value = false
    }
}
