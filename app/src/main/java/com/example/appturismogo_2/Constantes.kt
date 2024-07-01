package com.example.appturismogo_2

object Constantes {

    fun obtenertiempodevice(): Long{
        return System.currentTimeMillis()
    }


    const val anuncio_disponible = "Disponible"
    const val anuncio_nodisponible = "No Disponible"

    val categorias = arrayOf(
        "Restaurantes",
        "Paeeos y Excursiones",
        "Hoteles y Hospedajes"
    )
}