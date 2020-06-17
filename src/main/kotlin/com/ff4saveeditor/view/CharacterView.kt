package com.ff4saveeditor.view

import com.ff4saveeditor.model.Equipment
import com.ff4saveeditor.model.SaveFileController
import javafx.collections.FXCollections
import tornadofx.*

//Repeatable fragment for character tabs
class CharacterView(index:Int, saveFileCtrl:SaveFileController):Fragment() {
    private val saveFile = saveFileCtrl.saveFile
    override val root = form {
        fieldset("Stats: ") {
            field("Level: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.level })
                }
            }

            field("HP: ") {
                textfield() {
                    minWidth = 50.0
                    maxWidth = 50.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.currHP })
                }

                label("/")

                textfield() {
                    minWidth = 50.0
                    maxWidth = 50.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.maxHP })
                }
            }

            field("MP: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.currMP })
                }

                label("/")

                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.maxMP })
                }
            }

            field("Strength: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.strength })
                }
            }

            field("Speed: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.speed })
                }
            }

            field("Stamina: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.stamina })
                }
            }

            field("Intellect: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.intellect })
                }
            }

            field("Spirit: ") {
                textfield() {
                    minWidth = 40.0
                    maxWidth = 40.0
                    bind(saveFile.currentSlot.select { it.characters[index].character.spirit })
                }
            }
        }

        fieldset("Equipment: ") {
            field("Right Hand: ") {
                combobox<String> {
                    bind(saveFile.currentSlot.select { it.characters[index].character.rightHand })
                    this.items = FXCollections.observableArrayList(Equipment.handMap.values)
                }
            }

            field("Left Hand: ") {
                combobox<String> {
                    bind(saveFile.currentSlot.select { it.characters[index].character.leftHand })
                    this.items = FXCollections.observableArrayList(Equipment.handMap.values)
                }
            }

            field("Head: ") {
                combobox<String> {
                    bind(saveFile.currentSlot.select { it.characters[index].character.head })
                    this.items = FXCollections.observableArrayList(Equipment.headMap.values)
                }
            }

            field("Body: ") {
                combobox<String> {
                    bind(saveFile.currentSlot.select { it.characters[index].character.body })
                    this.items = FXCollections.observableArrayList(Equipment.bodyMap.values)
                }
            }

            field("Arm: ") {
                combobox<String> {
                    bind(saveFile.currentSlot.select { it.characters[index].character.arm })
                    this.items = FXCollections.observableArrayList(Equipment.armMap.values)
                }
            }
        }
    }
}