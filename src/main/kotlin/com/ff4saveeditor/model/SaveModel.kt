package com.ff4saveeditor.model

import com.ff4saveeditor.app.SaveFile
import tornadofx.*

class SaveModel: ItemViewModel<SaveFile>() {
    var file = bind(SaveFile::saveFile)
}