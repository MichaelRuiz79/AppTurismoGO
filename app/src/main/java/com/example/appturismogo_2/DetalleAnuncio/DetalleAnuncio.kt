package com.example.appturismogo_2.DetalleAnuncio

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.appturismogo_2.Adaptadores.AdaptadorImgSlider
import com.example.appturismogo_2.Anuncios.CrearAnuncio
import com.example.appturismogo_2.Constantes
import com.example.appturismogo_2.MainActivity
import com.example.appturismogo_2.Modelo.ModeloAnuncio
import com.example.appturismogo_2.Modelo.ModeloImgSlider
import com.example.appturismogo_2.R
import com.example.appturismogo_2.databinding.ActivityDetalleAnuncioBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.HashMap

class DetalleAnuncio : AppCompatActivity() {

private lateinit var binding : ActivityDetalleAnuncioBinding
private lateinit var firebaseAuth: FirebaseAuth
private var idAnuncio = ""

private var uidVendedor = ""
private var telVendedor = ""

private var favorito = false

private lateinit var imagenSliderArrayList : ArrayList<ModeloImgSlider>

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetalleAnuncioBinding.inflate(layoutInflater)
    setContentView(binding.root)




    firebaseAuth = FirebaseAuth.getInstance()

    idAnuncio = intent.getStringExtra("idAnuncio").toString()

    Constantes.incrementarVistas(idAnuncio)



    comprobarAnuncioFav()
    cargarInfoAnuncio()
    cargarImgAnuncio()













}





private fun cargarInfoAnuncio(){
    var ref = FirebaseDatabase.getInstance().getReference("Anuncios")
    ref.child(idAnuncio)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val modeloAnuncio = snapshot.getValue(ModeloAnuncio::class.java)

                    uidVendedor = "${modeloAnuncio!!.uid}"
                    val titulo = modeloAnuncio.titulo_anuncio
                    val descripcion = modeloAnuncio.descripcion
                    val direccion = modeloAnuncio.direccion
                    val categoria = modeloAnuncio.categoria
                    val precio = modeloAnuncio.precio
                    val estado = modeloAnuncio.estado
                    val vista = modeloAnuncio.contadorVistas
                    val tiempo = modeloAnuncio.tiempo

                    val formatoFecha = Constantes.obtenerFecha(tiempo)

                    if (uidVendedor == firebaseAuth.uid){
                        //Si el usuario que ha realizado la publicación, visualiza
                        //la información del anuncio


                        binding.TxtDescrVendedor.visibility = View.GONE
                        binding.perfilVendedor.visibility = View.GONE
                    }else{


                        binding.TxtDescrVendedor.visibility = View.VISIBLE
                        binding.perfilVendedor.visibility = View.VISIBLE
                    }

                    //Seteamos la información en las vistas
                    binding.TvTitulo.text = titulo
                    binding.TvDescr.text = descripcion
                    binding.TvDireccion.text = direccion
                    binding.TvCat.text = categoria
                    binding.TvPrecio.text = precio
                    binding.TvEstado.text = estado
                    binding.TvFecha.text = formatoFecha
                    binding.TvVistas.text = vista.toString()

                    if (estado.equals("Disponible")){
                        binding.TvEstado.setTextColor(Color.BLUE)
                    }else{
                        binding.TvEstado.setTextColor(Color.RED)
                    }

                    //Información del vendedor
                    cargarInfoVendedor()


                }catch (e:Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

}

private fun marcarAnuncioVendido(){
    val hashMap = HashMap<String, Any>()
    hashMap["estado"] = "${Constantes.anuncio_vendido}"

    val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
    ref.child(idAnuncio)
        .updateChildren(hashMap)
        .addOnSuccessListener {
            Toast.makeText(this,
                "El anuncio ha sido marcado como vendido",
                Toast.LENGTH_SHORT)
                .show()
        }
        .addOnFailureListener {e->
            Toast.makeText(this,
                "No se marcó como vendido debido a ${e.message}",
                Toast.LENGTH_SHORT)
                .show()
        }
}



private fun cargarInfoVendedor() {
    val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
    ref.child(uidVendedor)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val telefono = "${snapshot.child("telefono").value}"
                val codTel = "${snapshot.child("codigoTelefono").value}"
                val nombres = "${snapshot.child("nombres").value}"
                val imagenPerfil = "${snapshot.child("urlImagenPerfil").value}"
                val tiempo_reg = snapshot.child("tiempo").value as Long

                val for_fecha = Constantes.obtenerFecha(tiempo_reg)

                telVendedor = "$codTel$telefono"




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


}

private fun cargarImgAnuncio(){
    imagenSliderArrayList = ArrayList()

    val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
    ref.child(idAnuncio).child("Imagenes")
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imagenSliderArrayList.clear()
                for (ds in snapshot.children){
                    try {
                        val modeloImgSlider = ds.getValue(ModeloImgSlider::class.java)
                        imagenSliderArrayList.add(modeloImgSlider!!)
                    }catch (e:Exception){

                    }
                }

                val adaptadorImgSlider = AdaptadorImgSlider(this@DetalleAnuncio,imagenSliderArrayList)
                binding.imagenSliderVP.adapter = adaptadorImgSlider

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
}

private fun comprobarAnuncioFav(){
    val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
    ref.child("${firebaseAuth.uid}").child("Favoritos").child(idAnuncio)
        .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favorito = snapshot.exists()


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
}

private fun eliminarAnuncio(){
    val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
    ref.child(idAnuncio)
        .removeValue()
        .addOnSuccessListener {
            startActivity(Intent(this@DetalleAnuncio, MainActivity::class.java))
            finishAffinity()
            Toast.makeText(
                this,
                "Se eliminó el anuncio con éxito",
                Toast.LENGTH_SHORT
            ).show()
        }
        .addOnFailureListener {e->
            Toast.makeText(
                this,
                "${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }

}




}