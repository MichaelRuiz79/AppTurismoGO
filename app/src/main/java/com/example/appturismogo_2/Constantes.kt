package com.example.appturismogo_2

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

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

    fun obtenerTiempoDis() : Long{
        return System.currentTimeMillis()
    }

    fun obtenerFecha(tiempo : Long) : String{
        val calendario = Calendar.getInstance(Locale.ENGLISH)
        calendario.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendario).toString()
    }

}