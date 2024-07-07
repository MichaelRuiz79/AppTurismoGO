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
        "Todos",
        "Restaurantes",
        "Paseos y Excursiones",
        "Hoteles y Hospedajes"
    )
    val departamentos = arrayOf(
        "Lima",
        "Ayacucho",
        "Arequipa",
        "Ica",
        "Tumbes",
        "Piura",
        "Cusco"
    )

    val categoriasIcono = arrayOf(
        R.drawable.ic_categoria_todos,
        R.drawable.ic_categoria_rest,
        R.drawable.ic_categoria_paseos,
        R.drawable.ic_categoria_hotel,
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