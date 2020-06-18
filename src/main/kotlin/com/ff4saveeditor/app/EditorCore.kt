package com.ff4saveeditor.app

import com.ff4saveeditor.view.MainView
import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.App

class EditorCore: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1200.0
        stage.height = 800.0
        stage.minWidth = 950.0
        stage.minHeight = 500.0
        stage.isResizable = true
    }
}