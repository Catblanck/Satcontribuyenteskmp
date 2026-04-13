package com.example.satcontribuyenteskmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.satcontribuyenteskmp.App
import com.example.satcontribuyenteskmp.data.DatabaseDriverFactory

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "SAT Contribuyentes") {
        val driverFactory = DatabaseDriverFactory()
        App(driverFactory)
    }
}