package com.ff4saveeditor.view

import com.ff4saveeditor.model.SaveFile
import com.ff4saveeditor.model.SaveFileController
import com.sun.jnlp.ApiDialog
import javafx.application.Platform
import javafx.event.Event
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainView: View("FFIV Save Editor") {
    private val saveFileCtrl: SaveFileController by inject()
    private val saveFile = saveFileCtrl.saveFile
    private val saveSlots = saveFile.saveSlotControllers.value

    override val root = form {
        menubar {
            menu("File") {
                item("Open").action {
                    val fileChooser = FileChooser()
                    fileChooser.title = "Open Save File"
                    fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Binary Files", "*.bin"))
                    val f = fileChooser.showOpenDialog(null)
                    if (f != null) {
                        saveFileCtrl.loadSave(f)
                    }
                }

                item("Save")

                separator()

                item("Quit").action {
                    Platform.exit()
                }
            }

            menu("Save Slot") {
                enableWhen(saveFile.loaded)
                checkmenuitem("Slot 1") {
                    bind(saveSlots[0].saveSlot.selected)
                    setOnAction {
                        saveFileCtrl.chooseSlot(0)
                    }
                }

                checkmenuitem("Slot 2") {
                    bind(saveSlots[1].saveSlot.selected)
                    setOnAction {
                        saveFileCtrl.chooseSlot(1)
                    }
                }

                checkmenuitem("Slot 3") {
                    bind(saveSlots[2].saveSlot.selected)
                    setOnAction {
                        saveFileCtrl.chooseSlot(2)
                    }
                }
            }
        }
        fieldset("General") {
            field("Gil") {
                textfield() {
                    bind(saveFile.currentSlot.select { it.saveSlot.gil })
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
