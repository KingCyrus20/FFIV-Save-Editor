package com.ff4saveeditor.view

import com.ff4saveeditor.app.InventoryEvent
import com.ff4saveeditor.app.InventoryRequest
import com.ff4saveeditor.model.*
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import javafx.util.StringConverter
import tornadofx.*
import java.io.File

class MainView: View("FFIV Save Editor") {
    private val saveFileCtrl: SaveFileController by inject()
    private val saveFile = saveFileCtrl.saveFile
    private val saveSlots = saveFile.saveSlotControllers.value
    private val characterViews = List<CharacterView>(14) { i -> CharacterView(i, saveFileCtrl) }
    private val characterNames = listOf("Cecil (Dark Knight)", "Cecil (Paladin)", "Kain", "Rosa", "Rydia (Child)", "Rydia (Adult)", "Tellah",
            "Edward", "Porom", "Palom", "Yang", "Cid", "Edge", "Fusoya")

    override val root = form {
        paddingAll = 0
        menubar {
            menu("File") {
                item("Open").action {
                    val fileChooser = FileChooser()
                    fileChooser.title = "Open Save File"
                    fileChooser.initialDirectory = File(System.getenv("LOCALAPPDATA") + "\\FF4")
                    fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Binary Files", "*.bin"))
                    val f = fileChooser.showOpenDialog(currentWindow)
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
                form {
                    vbox(50) {
                        fieldset("Change Item Quantity") {
                            field("Item") {
                                combobox<InventoryEntry>(saveFile.currentSlot.select { it.saveSlot.selectedItem }) {
                                    isEditable = true
                                    makeAutocompletable()
                                    subscribe<InventoryEvent> { event ->
                                        items.setAll(event.inventory)
                                    }
                                    converter = ItemConverter(items)
                                }
                            }

                            field("Quantity") {
                                textfield {
                                    maxWidth = 40.0
                                    minWidth = 40.0
                                    bind(saveFile.currentSlot.select { it.saveSlot.selectedItem.select { it.quantityProperty } })
                                }
                            }
                        }

                        fieldset("Add Item") {
                            field("Item") {
                                combobox<String>() {
                                    isEditable = true
                                    makeAutocompletable()
                                    items.setAll(Items.universalMap.values)
                                }
                            }

                            field("Quantity") {
                                textfield {
                                    maxWidth = 40.0
                                    minWidth = 40.0
                                }
                            }

                            button("Add") {

                            }
                        }
                    }
                }
            }

            tab("Bestiary") {
                isClosable = false
                tableview(saveFile.currentSlot.select { it.saveSlot.bestiary }){
                    column("Name", BestiaryEntry::nameProperty) {
                        isEditable = false
                        prefWidth(150.0)
                    }

                    column("Seen", BestiaryEntry::seenProperty) {
                        useCheckbox(true)
                    }

                    column("New", BestiaryEntry::newProperty) {
                        useCheckbox(true)
                    }

                    column("Number Slain", BestiaryEntry::numSlainProperty) {
                        makeEditable()
                        prefWidth(100.0)
                    }
                }
                hgrow = Priority.ALWAYS
                vgrow = Priority.ALWAYS
            }
        }
    }
}
