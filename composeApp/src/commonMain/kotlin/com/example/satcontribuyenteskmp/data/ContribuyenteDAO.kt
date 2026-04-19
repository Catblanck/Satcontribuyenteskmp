package com.example.satcontribuyenteskmp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.satcontribuyenteskmp.database.AppDatabase
import com.example.satcontribuyenteskmp.database.Contribuyente
import com.example.satcontribuyenteskmp.database.Estado
import com.example.satcontribuyenteskmp.database.Municipio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

internal class ContribuyenteDAO(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val queries = database.appDatabaseQueries

    fun obtenerEstadosFlow(): Flow<List<Estado>> =
        queries.getEstados().asFlow().mapToList(Dispatchers.IO)

    fun obtenerMunicipiosPorEstadoFlow(estadoId: Long): Flow<List<Municipio>> =
        queries.getMunicipiosByEstado(estadoId).asFlow().mapToList(Dispatchers.IO)

    fun obtenerContribuyentesFlow(): Flow<List<Contribuyente>> =
        queries.getContribuyentes().asFlow().mapToList(Dispatchers.IO)

    fun insertarEstado(id: Long, nombre: String) {
        queries.insertEstado(id, nombre)
    }

    fun insertarMunicipio(id: Long, nombre: String, estadoId: Long) {
        queries.insertMunicipio(id, nombre, estadoId)
    }

    fun insertarContribuyente(
        rfc: String,
        nombre: String,
        razonSocial: String?,
        tipoPersona: String,
        codigoPostal: String,
        colonia: String,
        calle: String,
        numeroExterior: String,
        numeroInterior: String,
        actividadEconomica: String,
        regimenFiscal: String,
        estadoId: Long,
        municipioId: Long
    ) {
        queries.insertContribuyente(
            rfc,
            nombre,
            razonSocial,
            tipoPersona,
            codigoPostal,
            colonia,
            calle,
            numeroExterior,
            numeroInterior,
            actividadEconomica,
            regimenFiscal,
            estadoId,
            municipioId
        )
    }

    fun eliminarContribuyente(id: Long) {
        queries.deleteContribuyente(id)
    }

    fun obtenerContribuyentesConUbicacionFlow(rfc: String) =
        queries.getContribuyentesConUbicacion(rfc)
            .asFlow()
            .mapToList(Dispatchers.IO)

    fun buscarPorId(id: Long) =
        queries.buscarPorId(id).executeAsOneOrNull()

    fun actualizarContribuyente(
        id: Long,
        rfc: String,
        nombre: String,
        razonSocial: String?,
        tipoPersona: String,
        codigoPostal: String,
        colonia: String,
        calle: String,
        numeroExterior: String,
        numeroInterior: String,
        actividadEconomica: String,
        regimenFiscal: String,
        estadoId: Long,
        municipioId: Long
    ) {
        queries.updateContribuyente(
            rfc,
            nombre,
            razonSocial,
            tipoPersona,
            codigoPostal,
            colonia,
            calle,
            numeroExterior,
            numeroInterior,
            actividadEconomica,
            regimenFiscal,
            estadoId,
            municipioId,
            id
        )
    }
}


