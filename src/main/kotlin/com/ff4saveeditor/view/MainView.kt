package com.ff4saveeditor.view

import javafx.stage.FileChooser
import tornadofx.*

class MainView : View("FFIV Save Editor") {
    override val root = vbox {
        menubar {
            menu("File") {
                item("Open").action {
                    val fileChooser = FileChooser()
                    fileChooser.title = "Open Save File"
                    fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Binary Files", "*.bin"))
                    val saveFile = fileChooser.showOpenDialog(null)
                }
                item("Save")
                item("Quit")
            }
        }
        tabpane {
            tab("Slot 1") {

            }

            tab("Slot 2") {

            }

            tab("Slot 3") {

            }
        }
    }
}
