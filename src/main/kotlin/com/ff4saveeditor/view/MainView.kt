package com.ff4saveeditor.view

import com.ff4saveeditor.model.SaveModel
import javafx.application.Platform
import javafx.stage.FileChooser
import tornadofx.*

class MainView: View("FFIV Save Editor") {
    val saveModel: SaveModel by inject()
    override val root = vbox {
        menubar {
            menu("File") {
                item("Open").action {
                    val fileChooser = FileChooser()
                    fileChooser.title = "Open Save File"
                    fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Binary Files", "*.bin"))
                    saveModel.file.value = fileChooser.showOpenDialog(null)
                }

                item("Save")

                separator()

                item("Quit").action {
                    Platform.exit()
                }
            }

            menu("Save Slot") {
                enableWhen(saveModel.dirty)
                checkmenuitem("Slot 1") {

                }

                checkmenuitem("Slot 2") {

                }

                checkmenuitem("Slot 3") {

                }
            }
        }
        tabpane {
            tab("Characters") {
                isClosable = false
            }

            tab("Inventory") {
                isClosable = false
            }

            tab("Bestiary") {
                isClosable = false
            }
        }
    }
}
