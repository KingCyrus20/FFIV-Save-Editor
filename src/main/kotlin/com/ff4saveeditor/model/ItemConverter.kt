package com.ff4saveeditor.model

import javafx.util.StringConverter

class ItemConverter(private val inventory: List<InventoryEntry>): StringConverter<InventoryEntry>() {
    override fun fromString(string: String?) = inventory.firstOrNull() {it.name == string} ?: InventoryEntry(0, 0)
    override fun toString(item: InventoryEntry?) = item?.name ?: ""
}