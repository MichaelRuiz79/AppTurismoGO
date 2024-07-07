package com.example.appturismogo_2.Modelo

class ModeloAnuncio {

    var id : String = ""
    var uid : String = ""
    var titulo_anuncio : String = ""
    var categoria : String = ""
    var direccion : String = ""
    var precio : String = ""
    var descripcion : String = ""
    var estado : String = ""
    var tiempo : Long = 0
    var latitud = 0.0
    var longitud = 0.0
    var favorito = false
    var contadorVistas = 0

    constructor()
    constructor(
        id: String,
        uid: String,
        titulo_anuncio: String,
        categoria: String,
        direccion: String,
        precio: String,
        descripcion: String,
        estado: String,
        tiempo: Long,
        latitud: Double,
        longitud: Double,
        favorito: Boolean,
        contadorVistas: Int
    ) {
        this.id = id
        this.uid = uid
        this.titulo_anuncio = titulo_anuncio
        this.categoria = categoria
        this.direccion = direccion
        this.precio = precio
        this.descripcion = descripcion
        this.estado = estado
        this.tiempo = tiempo
        this.latitud = latitud
        this.longitud = longitud
        this.favorito = favorito
        this.contadorVistas = contadorVistas
    }


}