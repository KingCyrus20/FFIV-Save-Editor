package com.ff4saveeditor.view

import com.ff4saveeditor.model.Equipment
import com.ff4saveeditor.model.SaveFileController
import javafx.collections.FXCollections
import javafx.geometry.Pos
import tornadofx.*
import javax.swing.GroupLayout

//Repeatable fragment for character tabs
class CharacterView(index:Int, saveFileCtrl:SaveFileController):Fragment() {
    private val saveFile = saveFileCtrl.saveFile
    override val root = form {
        hbox(50) {
            fieldset("Stats: ") {
                field("Level: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.level })
                        filterInput { it.controlNewText.isInt() }
                    }
                }

                field("HP: ") {
                    textfield {
                        minWidth = 50.0
                        maxWidth = 50.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.currHP })
                    }

                    label("/")

                    textfield {
                        minWidth = 50.0
                        maxWidth = 50.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.maxHP })
                    }
                }

                field("MP: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.currMP })
                        filterInput { it.controlNewText.isInt() }
                    }

                    label("/")

                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.maxMP })
                        filterInput { it.controlNewText.isInt() }
                    }
                }

                field("Strength: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.strength })
                        filterInput { it.controlNewText.isInt() }
                    }
                }

                field("Speed: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.speed })
                        filterInput { it.controlNewText.isInt() }
                    }
                }

                field("Stamina: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.stamina })
                        filterInput { it.controlNewText.isInt() }
                    }
                }

                field("Intellect: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.intellect })
                        filterInput { it.controlNewText.isInt() }
                    }
                }

                field("Spirit: ") {
                    textfield {
                        minWidth = 40.0
                        maxWidth = 40.0
                        bind(saveFile.currentSlot.select { it.characters[index].character.spirit })
                        filterInput { it.controlNewText.isInt() }
                    }
                }
            }

            fieldset("Equipment: ") {
                field("Right Hand: ") {
                    combobox<String> {
                        this.items = FXCollections.observableArrayList(Equipment.handMap.values)
                        this.items.add(0, "Empty")
                        bind(saveFile.currentSlot.select { it.characters[index].character.rightHand })
                    }
                }

                field("Left Hand: ") {
                    combobox<String> {
                        this.items = FXCollections.observableArrayList(Equipment.handMap.values)
                        this.items.add(0, "Empty")
                        bind(saveFile.currentSlot.select { it.characters[index].character.leftHand })
                    }
                }

                field("Head: ") {
                    combobox<String> {
                        this.items = FXCollections.observableArrayList(Equipment.headMap.values)
                        this.items.add(0, "Empty")
                        bind(saveFile.currentSlot.select { it.characters[index].character.head })
                    }
                }

                field("Body: ") {
                    combobox<String> {
                        this.items = FXCollections.observableArrayList(Equipment.bodyMap.values)
                        this.items.add(0, "Empty")
                        bind(saveFile.currentSlot.select { it.characters[index].character.body })
                    }
                }

                field("Arm: ") {
                    combobox<String> {
                        this.items = FXCollections.observableArrayList(Equipment.armMap.values)
                        this.items.add(0, "Empty")
                        bind(saveFile.currentSlot.select { it.characters[index].character.arm })
                    }
                }
            }
        }
    }
}