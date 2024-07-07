package com.example.appturismogo_2

<<<<<<< Updated upstream
=======
import android.content.Context
import android.content.Intent
import android.net.Uri
>>>>>>> Stashed changes
import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object Constantes {

    fun obtenertiempodevice(): Long{
        return System.currentTimeMillis()
    }


    const val anuncio_disponible = "Disponible"
    const val anuncio_nodisponible = "No Disponible"
    const val anuncio_vendido = "Vendido"

    val categorias = arrayOf(
        "Todos",
        "Restaurantes",
        "Paseos y Excursiones",
        "Hoteles y Hospedajes"
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

<<<<<<< Updated upstream
=======
    fun agregarAnuncioFav (context: Context, idAnuncio : String){
        val firebaseAuth = FirebaseAuth.getInstance()
        val tiempo = Constantes.obtenerTiempoDis()

        val hashMap = HashMap<String, Any>()
        hashMap["idAnuncio"] = idAnuncio
        hashMap["tiempo"] = tiempo

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).child("Favoritos").child(idAnuncio)
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(context,
                    "Anuncio agregado a Favoritos",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(context,
                    "${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    fun eliminarAnuncioFav (context: Context, idAnuncio: String){
        val firebaseAuth = FirebaseAuth.getInstance()

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).child("Favoritos").child(idAnuncio)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context,
                    "Anuncio eliminado de Favoritos",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e->
                Toast.makeText(context,
                    "${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    fun llamarIntent (context: Context, tef : String){
        val intent = Intent(Intent.ACTION_CALL)
        intent.setData(Uri.parse("tel:$tef"))
        context.startActivity(intent)
    }

    fun incrementarVistas(idAnuncio : String){
        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.child(idAnuncio)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var vistasActuales = "${snapshot.child("contadorVistas").value}"
                    if (vistasActuales == "" || vistasActuales == "null"){
                        vistasActuales = "0"
                    }

                    val nuevaVista = vistasActuales.toLong()+ 1

                    val hashMap = java.util.HashMap<String, Any>()
                    hashMap["contadorVistas"] = nuevaVista

                    val dbRef = FirebaseDatabase.getInstance().getReference("Anuncios")
                    dbRef.child(idAnuncio)
                        .updateChildren(hashMap)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }



>>>>>>> Stashed changes
}