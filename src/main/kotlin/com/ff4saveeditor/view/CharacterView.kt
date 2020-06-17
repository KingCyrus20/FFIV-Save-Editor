package com.ff4saveeditor.view

import com.ff4saveeditor.model.SaveFileController
import tornadofx.*

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

                }
            }

            field("Left Hand: ") {
                combobox<String> {

                }
            }

            field("Head: ") {
                combobox<String> {

                }
            }

            field("Body: ") {
                combobox<String> {

                }
            }

            field("Arm: ") {
                combobox<String> {

                }
            }
        }
    }
}