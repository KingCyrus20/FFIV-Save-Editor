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
import java.nio.channels.FileChannel

class SaveSlot {
    val gilProperty = SimpleIntegerProperty()
    val selectedProperty = SimpleBooleanProperty()
    val hoursProperty = SimpleIntegerProperty()
    val minutesProperty = SimpleIntegerProperty()
    val secondsProperty = SimpleIntegerProperty()
    val inventoryProperty = SimpleListProperty<InventoryEntry>(FXCollections.observableArrayList<InventoryEntry>())
    val selectedItemProperty = SimpleObjectProperty<InventoryEntry>()
    val itemCountProperty = SimpleIntegerProperty()
    val bestiaryProperty = SimpleListProperty<BestiaryEntry>(FXCollections.observableArrayList<BestiaryEntry>())
    val characterControllers: ObservableList<CharacterController> = FXCollections.observableArrayList<CharacterController>()

    init {
        for (i in 1..14) characterControllers.add(CharacterController())
    }
}

class SaveSlotModel(saveSlot: SaveSlot) : ItemViewModel<SaveSlot>(saveSlot) {
    val gil = bind(SaveSlot::gilProperty)
    val selected = bind(SaveSlot::selectedProperty)
    val hours = bind(SaveSlot::hoursProperty)
    val minutes = bind(SaveSlot::minutesProperty)
    val seconds = bind(SaveSlot::secondsProperty)
    val inventory = bind(SaveSlot::inventoryProperty)
    val selectedItem = bind(SaveSlot::selectedItemProperty)
    val itemCount = bind(SaveSlot::itemCountProperty)
    val bestiary = bind(SaveSlot::bestiaryProperty)
    val characterControllers = bind(SaveSlot::characterControllers)
}

class SaveSlotScope: Scope() {
    val saveSlot = SaveSlotModel(SaveSlot())
}

class SaveSlotController: Controller() {
    private val saveSlotScope = SaveSlotScope()
    val saveSlot = saveSlotScope.saveSlot
    val characters: ObservableList<CharacterController> = saveSlot.characterControllers.value

    init {
        saveSlot.gil.value = 0
        saveSlot.selected.value = false
        saveSlot.hours.value = 0
        saveSlot.minutes.value = 0
        saveSlot.seconds.value = 0
        saveSlot.inventory.value = FXCollections.observableArrayList()
        saveSlot.itemCount.value = 0
        saveSlot.bestiary.value = FXCollections.observableArrayList()
    }

    fun select() {
        saveSlot.selected.value = true
    }

    fun deselect() {
        saveSlot.selected.value = false
    }

    fun clear() {
        saveSlot.gil.value = 0
        saveSlot.selected.value = false
        saveSlot.hours.value = 0
        saveSlot.minutes.value = 0
        saveSlot.seconds.value = 0
        saveSlot.inventory.value = FXCollections.observableArrayList()
        saveSlot.itemCount.value = 0
        saveSlot.bestiary.value = FXCollections.observableArrayList()
    }
}
