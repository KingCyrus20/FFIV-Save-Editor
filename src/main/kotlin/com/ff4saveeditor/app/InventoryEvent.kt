package com.ff4saveeditor.app

import com.ff4saveeditor.model.InventoryEntry
import tornadofx.*

//Event listened to by MainView inventory ComboBox to update items
class InventoryEvent(val inventory: List<InventoryEntry>) : FXEvent()