package com.example.satcontribuyenteskmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.satcontribuyenteskmp.data.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val driverFactory = DatabaseDriverFactory(this)

        setContent {
            App(driverFactory)
        }
    }
}