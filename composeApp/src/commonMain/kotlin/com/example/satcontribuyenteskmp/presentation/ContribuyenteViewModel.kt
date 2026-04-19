package com.example.satcontribuyenteskmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satcontribuyenteskmp.data.ContribuyenteDAO
import com.example.satcontribuyenteskmp.database.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
internal class ContribuyenteViewModel internal constructor(
    private val dao: ContribuyenteDAO
) : ViewModel() {

    private val _estados = MutableStateFlow<List<Estado>>(emptyList())
    val estados: StateFlow<List<Estado>> = _estados.asStateFlow()

    private val _municipios = MutableStateFlow<List<Municipio>>(emptyList())
    val municipios: StateFlow<List<Municipio>> = _municipios.asStateFlow()

    private val _contribuyentes = MutableStateFlow<List<GetContribuyentesConUbicacion>>(emptyList())

    val contribuyentes: StateFlow<List<GetContribuyentesConUbicacion>> = _contribuyentes

    init {
        cargarCatalogosIniciales()
        cargarContribuyentes()
    }

    fun cargarContribuyentes(rfc: String = "") {
        viewModelScope.launch {
            dao.obtenerContribuyentesConUbicacionFlow(rfc).collect { lista ->
                _contribuyentes.value = lista
            }
        }
    }

    suspend fun buscarPorIdDirecto(id: Long): Contribuyente? {
        return dao.buscarPorId(id)
    }

    fun cargarEstados() {
        viewModelScope.launch {
            dao.obtenerEstadosFlow().collect {
                _estados.value = it
            }
        }
    }

    fun cargarMunicipios(estadoId: Long) {
        viewModelScope.launch {
            dao.obtenerMunicipiosPorEstadoFlow(estadoId).collect {
                _municipios.value = it
            }
        }
    }

    fun buscarPorRfc(rfc: String) {
        viewModelScope.launch {
            dao.obtenerContribuyentesConUbicacionFlow(rfc).collect { lista ->
                _contribuyentes.value = lista
            }
        }
    }

    fun guardarContribuyente(
        rfc: String, nombre: String, razonSocial: String?, tipoPersona: String,
        codigoPostal: String, colonia: String, calle: String,
        numeroExterior: String, numeroInterior: String,
        actividadEconomica: String, regimenFiscal: String,
        estadoId: Long, municipioId: Long
    ) {
        viewModelScope.launch {
            dao.insertarContribuyente(
                rfc, nombre, razonSocial, tipoPersona,
                codigoPostal, colonia, calle,
                numeroExterior, numeroInterior,
                actividadEconomica, regimenFiscal,
                estadoId, municipioId
            )
            cargarContribuyentes()
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            dao.eliminarContribuyente(id)
            cargarContribuyentes()
        }
    }

    fun actualizarContribuyente(
        id: Long, rfc: String, nombre: String, razonSocial: String?, tipoPersona: String,
        codigoPostal: String, colonia: String, calle: String,
        numeroExterior: String, numeroInterior: String,
        actividadEconomica: String, regimenFiscal: String,
        estadoId: Long, municipioId: Long
    ) {
        viewModelScope.launch {
            dao.actualizarContribuyente(
                id, rfc, nombre, razonSocial, tipoPersona,
                codigoPostal, colonia, calle,
                numeroExterior, numeroInterior,
                actividadEconomica, regimenFiscal,
                estadoId, municipioId
            )
            cargarContribuyentes()
        }
    }



    fun cargarCatalogosIniciales() {
        viewModelScope.launch {
            if (dao.obtenerEstadosFlow().first().isEmpty()) {

                // Estados
                dao.insertarEstado(1, "Aguascalientes")
                dao.insertarEstado(2, "Baja California")
                dao.insertarEstado(3, "Baja California Sur")
                dao.insertarEstado(4, "Campeche")
                dao.insertarEstado(5, "Chiapas")
                dao.insertarEstado(6, "Chihuahua")
                dao.insertarEstado(7, "Ciudad de México")
                dao.insertarEstado(8, "Coahuila")
                dao.insertarEstado(9, "Colima")
                dao.insertarEstado(10, "Durango")
                dao.insertarEstado(11, "Estado de México")
                dao.insertarEstado(12, "Guanajuato")
                dao.insertarEstado(13, "Guerrero")
                dao.insertarEstado(14, "Hidalgo")
                dao.insertarEstado(15, "Jalisco")
                dao.insertarEstado(16, "Michoacán")
                dao.insertarEstado(17, "Morelos")
                dao.insertarEstado(18, "Nayarit")
                dao.insertarEstado(19, "Nuevo León")
                dao.insertarEstado(20, "Oaxaca")
                dao.insertarEstado(21, "Puebla")
                dao.insertarEstado(22, "Querétaro")
                dao.insertarEstado(23, "Quintana Roo")
                dao.insertarEstado(24, "San Luis Potosí")
                dao.insertarEstado(25, "Sinaloa")
                dao.insertarEstado(26, "Sonora")
                dao.insertarEstado(27, "Tabasco")
                dao.insertarEstado(28, "Tamaulipas")
                dao.insertarEstado(29, "Tlaxcala")
                dao.insertarEstado(30, "Veracruz")
                dao.insertarEstado(31, "Yucatán")
                dao.insertarEstado(32, "Zacatecas")

                // Municipios ejemplo por estado
                // 1 Aguascalientes
                dao.insertarMunicipio(1, "Aguascalientes", 1)
                dao.insertarMunicipio(2, "Jesús María", 1)
                dao.insertarMunicipio(3, "Calvillo", 1)

                // 2 Baja California
                dao.insertarMunicipio(4, "Mexicali", 2)
                dao.insertarMunicipio(5, "Tijuana", 2)
                dao.insertarMunicipio(6, "Ensenada", 2)

                // 3 Baja California Sur
                dao.insertarMunicipio(7, "La Paz", 3)
                dao.insertarMunicipio(8, "Los Cabos", 3)
                dao.insertarMunicipio(9, "Comondú", 3)

                // 4 Campeche
                dao.insertarMunicipio(10, "Campeche", 4)
                dao.insertarMunicipio(11, "Carmen", 4)
                dao.insertarMunicipio(12, "Champotón", 4)

                // 5 Chiapas
                dao.insertarMunicipio(13, "Tuxtla Gutiérrez", 5)
                dao.insertarMunicipio(14, "Tapachula", 5)
                dao.insertarMunicipio(15, "San Cristóbal", 5)

                // 6 Chihuahua
                dao.insertarMunicipio(16, "Chihuahua", 6)
                dao.insertarMunicipio(17, "Juárez", 6)
                dao.insertarMunicipio(18, "Delicias", 6)

                // 7 Ciudad de México
                dao.insertarMunicipio(19, "Coyoacán", 7)
                dao.insertarMunicipio(20, "Iztapalapa", 7)
                dao.insertarMunicipio(21, "Benito Juárez", 7)

                // 8 Coahuila
                dao.insertarMunicipio(22, "Saltillo", 8)
                dao.insertarMunicipio(23, "Torreón", 8)
                dao.insertarMunicipio(24, "Monclova", 8)

                // 9 Colima
                dao.insertarMunicipio(25, "Colima", 9)
                dao.insertarMunicipio(26, "Manzanillo", 9)
                dao.insertarMunicipio(27, "Tecomán", 9)

                // 10 Durango
                dao.insertarMunicipio(28, "Durango", 10)
                dao.insertarMunicipio(29, "Gómez Palacio", 10)
                dao.insertarMunicipio(30, "Lerdo", 10)

                // 11 Estado de México
                dao.insertarMunicipio(31, "Toluca", 11)
                dao.insertarMunicipio(32, "Ecatepec", 11)
                dao.insertarMunicipio(33, "Naucalpan", 11)

                // 12 Guanajuato
                dao.insertarMunicipio(34, "León", 12)
                dao.insertarMunicipio(35, "Irapuato", 12)
                dao.insertarMunicipio(36, "Celaya", 12)

                // 13 Guerrero
                dao.insertarMunicipio(37, "Acapulco", 13)
                dao.insertarMunicipio(38, "Chilpancingo", 13)
                dao.insertarMunicipio(39, "Iguala", 13)

                // 14 Hidalgo
                dao.insertarMunicipio(40, "Pachuca", 14)
                dao.insertarMunicipio(41, "Tulancingo", 14)
                dao.insertarMunicipio(42, "Tula", 14)

                // 15 Jalisco
                dao.insertarMunicipio(43, "Guadalajara", 15)
                dao.insertarMunicipio(44, "Zapopan", 15)
                dao.insertarMunicipio(45, "Tlaquepaque", 15)

                // 16 Michoacán
                dao.insertarMunicipio(46, "Morelia", 16)
                dao.insertarMunicipio(47, "Uruapan", 16)
                dao.insertarMunicipio(48, "Zamora", 16)

                // 17 Morelos
                dao.insertarMunicipio(49, "Cuernavaca", 17)
                dao.insertarMunicipio(50, "Jiutepec", 17)
                dao.insertarMunicipio(51, "Temixco", 17)

                // 18 Nayarit
                dao.insertarMunicipio(52, "Tepic", 18)
                dao.insertarMunicipio(53, "Bahía de Banderas", 18)
                dao.insertarMunicipio(54, "Santiago Ixcuintla", 18)

                // 19 Nuevo León
                dao.insertarMunicipio(55, "Monterrey", 19)
                dao.insertarMunicipio(56, "Guadalupe", 19)
                dao.insertarMunicipio(57, "San Nicolás", 19)

                // 20 Oaxaca
                dao.insertarMunicipio(58, "Oaxaca de Juárez", 20)
                dao.insertarMunicipio(59, "Salina Cruz", 20)
                dao.insertarMunicipio(60, "Juchitán", 20)

                // 21 Puebla
                dao.insertarMunicipio(61, "Puebla", 21)
                dao.insertarMunicipio(62, "Tehuacán", 21)
                dao.insertarMunicipio(63, "Atlixco", 21)

                // 22 Querétaro
                dao.insertarMunicipio(64, "Querétaro", 22)
                dao.insertarMunicipio(65, "San Juan del Río", 22)
                dao.insertarMunicipio(66, "Corregidora", 22)

                // 23 Quintana Roo
                dao.insertarMunicipio(67, "Cancún", 23)
                dao.insertarMunicipio(68, "Playa del Carmen", 23)
                dao.insertarMunicipio(69, "Chetumal", 23)

                // 24 San Luis Potosí
                dao.insertarMunicipio(70, "San Luis Potosí", 24)
                dao.insertarMunicipio(71, "Soledad", 24)
                dao.insertarMunicipio(72, "Matehuala", 24)

                // 25 Sinaloa
                dao.insertarMunicipio(73, "Culiacán", 25)
                dao.insertarMunicipio(74, "Mazatlán", 25)
                dao.insertarMunicipio(75, "Los Mochis", 25)

                // 26 Sonora
                dao.insertarMunicipio(76, "Hermosillo", 26)
                dao.insertarMunicipio(77, "Ciudad Obregón", 26)
                dao.insertarMunicipio(78, "Nogales", 26)

                // 27 Tabasco
                dao.insertarMunicipio(79, "Villahermosa", 27)
                dao.insertarMunicipio(80, "Comalcalco", 27)
                dao.insertarMunicipio(81, "Cárdenas", 27)

                // 28 Tamaulipas
                dao.insertarMunicipio(82, "Ciudad Victoria", 28)
                dao.insertarMunicipio(83, "Tampico", 28)
                dao.insertarMunicipio(84, "Reynosa", 28)

                // 29 Tlaxcala
                dao.insertarMunicipio(85, "Tlaxcala", 29)
                dao.insertarMunicipio(86, "Apizaco", 29)
                dao.insertarMunicipio(87, "Huamantla", 29)

                // 30 Veracruz
                dao.insertarMunicipio(88, "Xalapa", 30)
                dao.insertarMunicipio(89, "Veracruz", 30)
                dao.insertarMunicipio(90, "Coatzacoalcos", 30)

                // 31 Yucatán
                dao.insertarMunicipio(91, "Mérida", 31)
                dao.insertarMunicipio(92, "Valladolid", 31)
                dao.insertarMunicipio(93, "Progreso", 31)

                // 32 Zacatecas
                dao.insertarMunicipio(94, "Zacatecas", 32)
                dao.insertarMunicipio(95, "Fresnillo", 32)
                dao.insertarMunicipio(96, "Jerez", 32)
            }
            cargarEstados()
        }
    }
}


