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
    var tipoExpandido by remember { mutableStateOf(false) }
    var actividadExpandida by remember { mutableStateOf(false) }
    var regimenExpandido by remember { mutableStateOf(false) }

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

    val actividades = listOf(
        "Comercio al por menor",
        "Servicios profesionales",
        "Industria manufacturera",
        "Construcción",
        "Transporte",
        "Agricultura"
    )

    val regimenes = listOf(
        "Régimen Simplificado " +
                "de Confianza (RESICO)",
        "Sueldos y salarios",
        "Actividades empresariales",
        "Arrendamiento",
        "Régimen de Incorporación Fiscal",
        "Régimen General de Ley"
    )

    LaunchedEffect(id) {
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
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre campos
        ) {
            val modifier = Modifier.weight(1f) // Hace que todos midan lo mismo

            OutlinedTextField(rfc, { rfc = it }, label = { Text("RFC") }, modifier = modifier)
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = modifier)
            OutlinedTextField(razonSocial, { razonSocial = it }, label = { Text("Razón social") }, modifier = modifier)

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre campos
        ){
            val modifier = Modifier.weight(1f)

            OutlinedTextField(codigoPostal, { codigoPostal = it }, label = { Text("C.P.") }, modifier = modifier)
            OutlinedTextField(colonia, { colonia = it }, label = { Text("Colonia") }, modifier = modifier)
            OutlinedTextField(calle, { calle = it }, label = { Text("Calle") }, modifier = modifier)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val modifier = Modifier.weight(1f)

            OutlinedTextField(numeroExterior, { numeroExterior = it }, label = { Text("Núm. Exterior") }, modifier = modifier)
            OutlinedTextField(numeroInterior, { numeroInterior = it }, label = { Text("Núm. Interior") }, modifier = modifier)
            Column(modifier = Modifier.padding(16.dp)){
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(16.dp)){
                Text("Municipio")

                Box {
                    Button(onClick = { municipioExpandido = true },
                        enabled = estadoSeleccionado != null) {
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
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            Column(modifier = Modifier.padding(16.dp)){
                Text("Tipo de Persona")
                Box {
                    Button(onClick = { tipoExpandido = true }) {
                        Text(tipoPersona)
                    }

                    DropdownMenu(
                        expanded = tipoExpandido,
                        onDismissRequest = { tipoExpandido = false }
                    ) {
                        listOf("Física", "Moral").forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    tipoPersona = it
                                    tipoExpandido = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Actividad Económica")
                Box {
                    Button(onClick = { actividadExpandida = true }) {
                        Text(if (actividadEconomica.isBlank()) "Seleccionar" else actividadEconomica)
                    }

                    DropdownMenu(
                        expanded = actividadExpandida,
                        onDismissRequest = { actividadExpandida = false }
                    ) {
                        actividades.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    actividadEconomica = it
                                    actividadExpandida = false
                                }
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text("Régimen Fiscal")

                Box {
                    Button(onClick = { regimenExpandido = true }) {
                        Text(if (regimenFiscal.isBlank()) "Seleccionar" else regimenFiscal)
                    }

                    DropdownMenu(
                        expanded = regimenExpandido,
                        onDismissRequest = { regimenExpandido = false }
                    ) {
                        regimenes.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    regimenFiscal = it
                                    regimenExpandido = false
                                }
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {

            if (rfc.isBlank() ||codigoPostal.isBlank() || colonia.isBlank() ||
                calle.isBlank() || numeroExterior.isBlank()
            ) {
                error = "Campos de domicilio y RFC son obligatorios"
                return@Button
            }
            if (tipoPersona == "Física" && nombre.isBlank()) {
                error = "El nombre es obligatorio para persona física"
                return@Button
            }

            if (tipoPersona == "Moral" && razonSocial.isBlank()) {
                error = "La razón social es obligatoria para persona moral"
                return@Button
            }
            if (estadoSeleccionado == null || municipioSeleccionado == null) {
                error = "Selecciona estado y municipio"
                return@Button
            }

            if (actividadEconomica.isBlank()) {
                error = "Selecciona actividad económica"
                return@Button
            }

            if (regimenFiscal.isBlank()) {
                error = "Selecciona régimen fiscal"
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



