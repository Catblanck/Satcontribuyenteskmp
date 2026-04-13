package com.example.satcontribuyenteskmp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.satcontribuyenteskmp.presentation.ContribuyenteViewModel

@Composable
internal fun AgregarContribuyenteScreen(
    navController: NavController,
    viewModel: ContribuyenteViewModel,
    id: Long? = null
) {
    var rfc by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var razonSocial by remember { mutableStateOf("") }
    var tipoPersona by remember { mutableStateOf("Física") }

    var estadoExpandido by remember { mutableStateOf(false) }
    var municipioExpandido by remember { mutableStateOf(false) }

    var estadoSeleccionado by remember { mutableStateOf<Long?>(null) }
    var municipioSeleccionado by remember { mutableStateOf<Long?>(null) }
    var codigoPostal by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var numeroExterior by remember { mutableStateOf("") }
    var numeroInterior by remember { mutableStateOf("") }
    var actividadEconomica by remember { mutableStateOf("") }
    var regimenFiscal by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }


    val estados by viewModel.estados.collectAsStateWithLifecycle()
    val municipios by viewModel.municipios.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.cargarCatalogosIniciales()

        if (id != null) {
            val contribuyente = viewModel.buscarPorIdDirecto(id)
            
            contribuyente?.let {
                rfc = it.rfc
                nombre = it.nombre
                razonSocial = it.razon_social ?: ""
                codigoPostal = it.codigo_postal ?: ""
                colonia = it.colonia ?: ""
                calle = it.calle ?: ""
                numeroExterior = it.numero_exterior ?: ""
                numeroInterior = it.numero_interior ?: ""
                actividadEconomica = it.actividad_economica ?: ""
                regimenFiscal = it.regimen_fiscal ?: ""

                estadoSeleccionado = it.estado_id
                municipioSeleccionado = it.municipio_id

                viewModel.cargarMunicipios(it.estado_id)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarEstados()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre campos
        ) {
            val modifier = Modifier.weight(1f) // Hace que todos midan lo mismo

            OutlinedTextField(rfc, { rfc = it }, label = { Text("RFC") }, modifier = modifier)
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = modifier)
            OutlinedTextField(razonSocial, { razonSocial = it }, label = { Text("Razón social") }, modifier = modifier)
            OutlinedTextField(codigoPostal, { codigoPostal = it }, label = { Text("C.P.") }, modifier = modifier)
            OutlinedTextField(colonia, { colonia = it }, label = { Text("Colonia") }, modifier = modifier)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val modifier = Modifier.weight(1f)

            OutlinedTextField(calle, { calle = it }, label = { Text("Calle") }, modifier = modifier)
            OutlinedTextField(numeroExterior, { numeroExterior = it }, label = { Text("Núm. Exterior") }, modifier = modifier)
            OutlinedTextField(numeroInterior, { numeroInterior = it }, label = { Text("Núm. Interior") }, modifier = modifier)
            OutlinedTextField(actividadEconomica, { actividadEconomica = it }, label = { Text("Act. Economica") }, modifier = modifier)
            OutlinedTextField(regimenFiscal, { regimenFiscal = it }, label = { Text("Régimen") }, modifier = modifier)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Estado")

        Box {
            Button(onClick = { estadoExpandido = true }) {
                Text(
                    estados
                        .find { it.id == estadoSeleccionado }
                        ?.nombre ?: "Seleccionar estado"
                )
            }

            DropdownMenu(
                expanded = estadoExpandido,
                onDismissRequest = { estadoExpandido = false }
            ) {
                estados.forEach { estado ->
                    DropdownMenuItem(
                        text = { Text(estado.nombre) },
                        onClick = {
                            estadoSeleccionado = estado.id
                            municipioSeleccionado = null
                            estadoExpandido = false
                            viewModel.cargarMunicipios(estado.id)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Municipio")

        Box {
            Button(onClick = { municipioExpandido = true }) {
                Text(
                    municipios
                        .find { it.id == municipioSeleccionado }
                        ?.nombre ?: "Seleccionar municipio"
                )
            }

            DropdownMenu(
                expanded = municipioExpandido,
                onDismissRequest = { municipioExpandido = false }
            ) {
                municipios.forEach { municipio ->
                    DropdownMenuItem(
                        text = { Text(municipio.nombre) },
                        onClick = {
                            municipioSeleccionado = municipio.id
                            municipioExpandido = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            if (rfc.isBlank() || nombre.isBlank()) {
                error = "RFC y Nombre son obligatorios"
                return@Button
            }

            if (estadoSeleccionado == null || municipioSeleccionado == null) {
                error = "Selecciona estado y municipio"
                return@Button
            }

            error = ""

            if (id == null) {
                viewModel.guardarContribuyente(
                    rfc, nombre, razonSocial, tipoPersona,
                    codigoPostal, colonia, calle,
                    numeroExterior, numeroInterior,
                    actividadEconomica, regimenFiscal,
                    estadoSeleccionado!!, municipioSeleccionado!!
                )
            } else {
                viewModel.actualizarContribuyente(
                    id, rfc, nombre, razonSocial, tipoPersona,
                    codigoPostal, colonia, calle,
                    numeroExterior, numeroInterior,
                    actividadEconomica, regimenFiscal,
                    estadoSeleccionado!!, municipioSeleccionado!!
                )
            }

            navController.popBackStack()

        }) {
            Text(if (id == null) "Guardar" else "Actualizar")
        }

        if (error.isNotEmpty()) {
            Text(error, color = MaterialTheme.colorScheme.error)
        }
    }
}



