package com.ff4saveeditor.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class InventoryEntry(id: Int, quantity: Int) {
    val idProperty = SimpleIntegerProperty(id)
    var id by idProperty

    val nameProperty = SimpleStringProperty(Items.universalMap[id])
    var name by nameProperty

    val quantityProperty = SimpleIntegerProperty(quantity)
    var quantity by quantityProperty

    override fun equals(other: Any?): Boolean {
        if (other == null)
            return false
        other as InventoryEntry
        return name == other.name
    }

    override fun toString(): String{
        return name
    }
}