package com.ff4saveeditor.app

import com.ff4saveeditor.view.MainView
import javafx.stage.Stage
import tornadofx.App

class EditorCore: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 600.0
        stage.isResizable = false
    }
}