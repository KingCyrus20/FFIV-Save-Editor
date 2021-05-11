package com.ff4saveeditor.view

import com.ff4saveeditor.app.InventoryEvent
import com.ff4saveeditor.app.InventoryRequest
import com.ff4saveeditor.model.*
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import javafx.util.StringConverter
import tornadofx.*
import java.io.File

class MainView: View("FFIV Save Editor") {
    private val bigIntRegex = Regex("^(\\d+(,\\d+)*)?\$")
    private val saveFileCtrl: SaveFileController by inject()
    private val saveFile = saveFileCtrl.saveFile
    private val saveSlots = saveFile.saveSlotControllers.value
    private val characterViews = List<CharacterView>(14) { i -> CharacterView(i, saveFileCtrl) }
    private val characterNames = listOf("Cecil (Dark Knight)", "Cecil (Paladin)", "Kain", "Rosa", "Rydia (Child)", "Rydia (Adult)", "Tellah",
            "Edward", "Porom", "Palom", "Yang", "Cid", "Edge", "Fusoya")

    //Properties for add item fieldset
    private val addItem = SimpleStringProperty("")
    private val addItemQuantity = SimpleIntegerProperty(0)

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

                item("Save") {
                    enableWhen(saveFile.loaded)
                    action {
                        val backupNum = saveFileCtrl.writeSave()
                        dialog("Save Successful") {
                            text = if (backupNum == 0)
                                "Backup saved as " + saveFileCtrl.file?.toPath().toString() + ".BAK"
                            else
                                "Backup saved as " + saveFileCtrl.file?.toPath().toString() + ".BAK" + backupNum
                        }
                    }
                }

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
                        filterInput { it.controlNewText.matches(bigIntRegex) || (it.text.equals(",") && !it.controlNewText.equals(",")) }
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
                        filterInput { it.controlNewText.isInt() }
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
                        filterInput {it.controlNewText.isInt()}
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
                        filterInput {it.controlNewText.isInt()}
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
                                    filterInput { it.controlNewText.isInt() }
                                }
                            }
                        }

                        fieldset("Add Item") {
                            field("Item") {
                                combobox<String>(addItem) {
                                    isEditable = true
                                    makeAutocompletable()
                                    items.setAll(Items.universalMap.values)
                                }
                            }

                            field("Quantity") {
                                textfield {
                                    maxWidth = 40.0
                                    minWidth = 40.0
                                    bind(addItemQuantity)
                                    filterInput { it.controlNewText.isInt() }
                                }
                            }

                            button("Add") {
                                enableWhen(saveFile.loaded)
                                setOnAction {
                                    if (!Items.inverseUniversal.keys.contains(addItem.value)) {
                                        error( "Invalid Item",
                                                "Choose a valid item from the dropdown", buttons = *arrayOf(ButtonType.OK),
                                                owner = currentWindow, title = "")
                                    }

                                    else if (addItemQuantity.value !in 1..99) {
                                        error("Invalid Quantity", "Enter a number between 1 and 99",
                                                buttons = *arrayOf(ButtonType.OK), owner = currentWindow, title = "")
                                    }

                                    else {
                                        val newItem = Items.inverseUniversal[addItem.value]?.let { it1 -> InventoryEntry(it1, addItemQuantity.value) }
                                        if (saveFile.currentSlot.value.saveSlot.inventory.value.contains(newItem)) {
                                            error("Already have item", "This item is already in your inventory",
                                                buttons = *arrayOf(ButtonType.OK), owner = currentWindow, title = "")
                                        }

                                        else {
                                            saveFile.currentSlot.value.saveSlot.inventory.value.add(newItem)
                                            fire(InventoryRequest())
                                        }
                                    }
                                }
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
