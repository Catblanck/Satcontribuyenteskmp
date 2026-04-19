package com.example.satcontribuyenteskmp.ui.screens

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.satcontribuyenteskmp.presentation.ContribuyenteViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun ListaContribuyentesScreen(
    navController: NavController,
    viewModel: ContribuyenteViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.cargarContribuyentes()
    }

    var textoBusqueda by remember { mutableStateOf("") }
    val contribuyentes by viewModel.contribuyentes.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = {
            navController.navigate("agregar")
        }) {
            Text("Agregar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
                viewModel.cargarContribuyentes(it)
            },
            label = { Text("Buscar por RFC") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(contribuyentes) { contribuyente ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("RFC: ${contribuyente.rfc}")
                        Text("Nombre: ${contribuyente.nombre}")
                        Text("Estado: ${contribuyente.estado_nombre}")
                        Text("Municipio: ${contribuyente.municipio_nombre}")
                        Text("Razón social: ${contribuyente.razon_social ?: ""}")
                        Text("Actividad: ${contribuyente.actividad_economica ?: ""}")
                        Text("Régimen: ${contribuyente.regimen_fiscal ?: ""}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Button(
                                onClick = {
                                    navController.navigate("editar/${contribuyente.id}")
                                }
                            ) {
                                Text("Editar")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    viewModel.eliminar(contribuyente.id)
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}


