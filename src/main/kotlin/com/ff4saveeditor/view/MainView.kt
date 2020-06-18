package com.ff4saveeditor.view

import com.ff4saveeditor.model.InventoryEntry
import com.ff4saveeditor.model.Items
import com.ff4saveeditor.model.SaveFile
import com.ff4saveeditor.model.SaveFileController
import com.sun.jnlp.ApiDialog
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.event.Event
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.lang.IllegalArgumentException

class MainView: View("FFIV Save Editor") {
    private val saveFileCtrl: SaveFileController by inject()
    private val saveFile = saveFileCtrl.saveFile
    private val saveSlots = saveFile.saveSlotControllers.value
    private val characterViews = List<CharacterView>(14) { i -> CharacterView(i, saveFileCtrl) }
    private val characterNames = listOf("Cecil (Dark Knight)", "Cecil (Paladin)", "Kain", "Rosa", "Rydia (Child)", "Rydia (Adult)", "Tellah",
            "Edward", "Porom", "Palom", "Yang", "Cid", "Edge", "Fusoya")

    override val root = form {
        menubar {
            menu("File") {
                item("Open").action {
                    val fileChooser = FileChooser()
                    fileChooser.title = "Open Save File"
                    fileChooser.initialDirectory = File(System.getenv("LOCALAPPDATA") + "\\FF4")
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
        hbox {
            fieldset {
                field("Gil") {
                    textfield {
                        bind(saveFile.currentSlot.select { it.saveSlot.gil })
                    }
                }
                paddingHorizontal = 10
            }
            label("Playtime:") {
                paddingVertical = 15
            }
            fieldset {
                field("H:") {
                    textfield {
                        bind(saveFile.currentSlot.select { it.saveSlot.hours })
                        minWidth = 40.0
                        maxWidth = 40.0
                    }
                }
                paddingHorizontal = 10
            }
            fieldset {
                field("M:") {
                    textfield {
                        bind(saveFile.currentSlot.select { it.saveSlot.minutes })
                        minWidth = 40.0
                        maxWidth = 40.0
                    }
                }
                paddingHorizontal = 10
            }
            fieldset {
                field("S:") {
                    textfield {
                        bind(saveFile.currentSlot.select { it.saveSlot.seconds })
                        minWidth = 40.0
                        maxWidth = 40.0
                    }
                }
                paddingHorizontal = 10
            }
        }
        tabpane {
            //Create character tabs
            for (i in 0..13) {
                val cTab = tab(characterNames[i])
                cTab.add(characterViews[i])
                cTab.isClosable = false
            }

            tab("Inventory") {
                isClosable = false
                tableview(saveFile.currentSlot.select { it.saveSlot.inventory }){
                    column("Item", InventoryEntry::nameProperty) {
                        makeEditable()
                        prefWidth(200.0)
                        useComboBox(FXCollections.observableArrayList(Items.universalMap.values))
                    }

                    column("Quantity", InventoryEntry::quantityProperty) {
                        makeEditable()
                    }
                }
            }

            tab("Bestiary") {
                isClosable = false
            }
        }
    }
}
