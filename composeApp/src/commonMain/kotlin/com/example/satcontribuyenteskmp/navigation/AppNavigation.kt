package com.example.satcontribuyenteskmp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.satcontribuyenteskmp.presentation.ContribuyenteViewModel
import com.example.satcontribuyenteskmp.ui.screens.AgregarContribuyenteScreen
import com.example.satcontribuyenteskmp.ui.screens.ListaContribuyentesScreen

@Composable
internal fun AppNavigation(
    viewModel: ContribuyenteViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {
        composable("lista") {
            ListaContribuyentesScreen(navController, viewModel)
        }

        composable("agregar") {
            AgregarContribuyenteScreen(navController, viewModel)
        }

        composable("editar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("id")
                ?.toLong() ?: 0L

            AgregarContribuyenteScreen(
                navController,
                viewModel,
                id
            )
        }
    }
}



