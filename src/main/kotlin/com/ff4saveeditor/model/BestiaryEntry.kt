package com.ff4saveeditor.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class BestiaryEntry(name: String?, isSeen: Boolean, isNew:Boolean, numSlain: Int) {
    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty

    val seenProperty = SimpleBooleanProperty(isSeen)
    var seen by seenProperty

    val newProperty = SimpleBooleanProperty(isNew)
    var isNew by newProperty

    val numSlainProperty = SimpleIntegerProperty(numSlain)
    var numSlain by numSlainProperty
}