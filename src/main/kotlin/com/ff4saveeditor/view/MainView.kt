package com.ff4saveeditor.view

import com.ff4saveeditor.model.SaveFile
import com.ff4saveeditor.model.SaveFileController
import com.sun.jnlp.ApiDialog
import javafx.application.Platform
import javafx.event.Event
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainView: View("FFIV Save Editor") {
    private val saveFileCtrl: SaveFileController by inject()
    private val saveFile = saveFileCtrl.saveFile
    private val saveSlots = saveFile.saveSlotControllers.value
    val characterViews = List<CharacterView>(14) { i -> CharacterView(i, saveFileCtrl) }

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
        hbox {
            fieldset() {
                field("Gil") {
                    textfield() {
                        bind(saveFile.currentSlot.select { it.saveSlot.gil })
                    }
                }
                paddingHorizontal = 10
            }
            label("Playtime:") {
                paddingVertical = 15
            }
            fieldset() {
                field("H:") {
                    textfield() {
                        bind(saveFile.currentSlot.select { it.saveSlot.hours })
                        minWidth = 40.0
                        maxWidth = 40.0
                    }
                }
                paddingHorizontal = 10
            }
            fieldset {
                field("M:") {
                    textfield() {
                        bind(saveFile.currentSlot.select { it.saveSlot.minutes })
                        minWidth = 40.0
                        maxWidth = 40.0
                    }
                }
                paddingHorizontal = 10
            }
            fieldset {
                field("S:") {
                    textfield() {
                        bind(saveFile.currentSlot.select { it.saveSlot.seconds })
                        minWidth = 40.0
                        maxWidth = 40.0
                    }
                }
                paddingHorizontal = 10
            }
        }
        tabpane {
            tab("Cecil (Dark Knight)") {
                isClosable = false
                add(characterViews[0])
            }

            tab("Cecil (Paladin)") {
                isClosable = false
                add(characterViews[1])
            }

            tab("Kain") {
                isClosable = false
                add(characterViews[2])
            }

            tab("Rosa") {
                isClosable = false
                add(characterViews[3])
            }

            tab("Rydia (Child)") {
                isClosable = false
                add(characterViews[4])
            }

            tab("Rydia (Adult)") {
                isClosable = false
                add(characterViews[5])
            }

            tab("Tellah") {
                isClosable = false
                add(characterViews[6])
            }

            tab("Edward") {
                isClosable = false
                add(characterViews[7])
            }

            tab("Porom") {
                isClosable = false
                add(characterViews[8])
            }

            tab("Palom") {
                isClosable = false
                add(characterViews[9])
            }

            tab("Yang") {
                isClosable = false
                add(characterViews[10])
            }

            tab("Cid") {
                isClosable = false
                add(characterViews[11])
            }

            tab("Edge") {
                isClosable = false
                add(characterViews[12])
            }

            tab("Fusoya") {
                isClosable = false
                add(characterViews[13])
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
