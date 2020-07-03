package com.ff4saveeditor.app

import tornadofx.*

//Fired when save loaded or slot selected to get current slot's inventory
class InventoryRequest : FXEvent(EventBus.RunOn.BackgroundThread) {
}