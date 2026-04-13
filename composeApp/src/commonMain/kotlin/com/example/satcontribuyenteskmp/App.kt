package com.example.satcontribuyenteskmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.satcontribuyenteskmp.data.ContribuyenteDAO
import com.example.satcontribuyenteskmp.data.DatabaseDriverFactory
import com.example.satcontribuyenteskmp.navigation.AppNavigation
import com.example.satcontribuyenteskmp.presentation.ContribuyenteViewModel

@Composable
fun App(databaseDriverFactory: DatabaseDriverFactory) {
    MaterialTheme {
        val dao = remember { ContribuyenteDAO(databaseDriverFactory) }
        val viewModel = viewModel { ContribuyenteViewModel(dao) }

        AppNavigation(viewModel)
    }
}



