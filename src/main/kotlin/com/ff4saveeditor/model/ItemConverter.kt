package com.ff4saveeditor.model

import javafx.util.StringConverter

class ItemConverter(private val inventory: List<InventoryEntry>): StringConverter<InventoryEntry>() {
    override fun fromString(string: String?): InventoryEntry {
        inventory.forEach {
            if (it.name == string) {
                return it
            }
        }
        return InventoryEntry(0,0)
    }

    override fun toString(item: InventoryEntry?): String {
        return if (item == null) ""
        else item.name
    }
}