package com.ff4saveeditor.app

import tornadofx.*

//Fired when save loaded, slot selected to get current slot's inventory, or inventory updated
class InventoryRequest : FXEvent(EventBus.RunOn.BackgroundThread)