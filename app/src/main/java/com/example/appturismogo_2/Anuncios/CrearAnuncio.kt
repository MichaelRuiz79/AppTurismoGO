package com.example.appturismogo_2.Anuncios

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.ComponentDialog
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appturismogo_2.Adaptadores.AdaptadorImagenSeleccionada
import com.example.appturismogo_2.Constantes
import com.example.appturismogo_2.Modelo.ModeloImageSeleccionada
import com.example.appturismogo_2.R
import com.example.appturismogo_2.databinding.ActivityCrearAnuncioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class CrearAnuncio : AppCompatActivity() {

    private lateinit var binding: ActivityCrearAnuncioBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var imagenUri : Uri?=null

    private lateinit var imagenSelecArrayList : ArrayList<ModeloImageSeleccionada>
    private lateinit var adaptadorImagenSel : AdaptadorImagenSeleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearAnuncioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        val adaptadorCat = ArrayAdapter(this, R.layout.item_categoria, Constantes.categorias)
        binding.Categoria.setAdapter(adaptadorCat)

        imagenSelecArrayList = ArrayList()
        cargarImagenes()

        binding.agrgarImagen.setOnClickListener{
            mostrarOpciones()

        }

        binding.BtnCrearAnuncio.setOnClickListener{
            validardatos()
        }

    }
    private var nombre = ""
    private var categoria = ""
    private var direccion = ""
    private var precio = ""
    private var descripcion = ""
    private var latitud = 0.0
    private var longitud = 0.0

    private fun validardatos(){
        nombre = binding.EtNombreAnuncio.text.toString()
        categoria = binding.Categoria.text.toString().trim()
        direccion = binding.locacion.text.toString().trim()
        precio = binding.precio.text.toString().trim()
        descripcion = binding.descripcion.toString().trim()

        if (categoria.isEmpty()){
            binding.Categoria.error = "Ingrese una categoria"
            binding.Categoria.requestFocus()
        } //else if (direccion.isEmpty()){
            //binding.locacion.error = "Ingrese una locación"
            //binding.locacion.requestFocus()
        //}
        else if (precio.isEmpty()){
            binding.precio.error = "Ingrese un precio"
            binding.precio.requestFocus()
        }
        else if (nombre.isEmpty()){
            binding.EtNombreAnuncio.error = "Ingrese el titulo de su anuncio"
            binding.EtNombreAnuncio.requestFocus()
        }
        else if (descripcion.isEmpty()){
            binding.descripcion.error = "Ingrese una descripción"
            binding.descripcion.requestFocus()
        }else if (imagenUri == null){
            Toast.makeText(this, "Agregue al menos una imagen", Toast.LENGTH_SHORT).show()
        }else{
            agregarAnuncio()
        }

    }

    private fun agregarAnuncio() {
        progressDialog.setMessage("Agregando anuncio")
        progressDialog.show()

        val tiempo = Constantes.obtenerTiempoDis()

        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        val keyId = ref.push().key

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "${keyId}"
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["titulo_anuncio"] = "${nombre}"
        hashMap["categoria"] = "${categoria}"
        hashMap["direccion"] = "${direccion}"
        hashMap["precio"] = "${precio}"
        hashMap["descripcion"] = "${descripcion}"
        hashMap["estado"] = "${Constantes.anuncio_disponible}"
        hashMap["tiempo"] = tiempo
        hashMap["latitud"] = latitud
        hashMap["longitud"] = longitud
        hashMap["contadorVistas"] = 0

        ref.child(keyId!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                cargarImagenesStorage(keyId)
            }
            .addOnFailureListener {e->
                Toast.makeText(
                    this, "${e.message}",Toast.LENGTH_SHORT
                ).show()
            }

    }

    private fun cargarImagenesStorage(keyId: String){
        for (i in imagenSelecArrayList.indices){
            val modeloImageSeleccionada = imagenSelecArrayList[i]
            val nombreImagen = modeloImageSeleccionada.id
            val rutaNombreImagen = "Anuncios/$nombreImagen"

            val storageReference = FirebaseStorage.getInstance().getReference(rutaNombreImagen)
            storageReference.putFile(modeloImageSeleccionada.imagenUri!!)
                .addOnSuccessListener {taskSnaphot->
                    val uriTask = taskSnaphot.storage.downloadUrl
                    while(!uriTask.isSuccessful);
                    val urlImgCargada = uriTask.result

                    if (uriTask.isSuccessful){
                        val hasMap = HashMap<String, Any>()
                        hasMap["id"] = "${modeloImageSeleccionada.imagenUri}"
                        hasMap["imageUrl"] = "$urlImgCargada"

                        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
                        ref.child(keyId).child("Imagenes")
                            .child(nombreImagen)
                            .updateChildren(hasMap)
                    }
                    progressDialog.dismiss()
                    onBackPressedDispatcher.onBackPressed() // redirige a la actividad anterior
                    Toast.makeText(this, "Su anuncio se publicó correctamente", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener{e->
                    Toast.makeText(
                        this, "${e.message}",Toast.LENGTH_SHORT
                    ).show()
                }
        }


    }

    private fun mostrarOpciones() {
        val popupMenu = PopupMenu(this, binding.agrgarImagen)
        popupMenu.menu.add(Menu.NONE, 1, 1, "Cámara")
        popupMenu.menu.add(Menu.NONE, 2, 2, "Galería")

        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item ->
            val itemId = item.itemId
            if (itemId == 1) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    solicitarPermisocamara.launch(arrayOf(android.Manifest.permission.CAMERA))
                }else{
                    solicitarPermisocamara.launch(arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ))
                }

            } else if (itemId == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    imagenGaleria()
                }else{
                    solicitarPermisoAlmacenamiento.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }

            }
            true
        }
    }
        private val solicitarPermisoAlmacenamiento = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){esConcedido->
            if (esConcedido){
                imagenGaleria()

            }else{
            Toast.makeText(
                this, "El permiso de almacenamiento ha sido denegado",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun imagenGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultadoGaleria_ARL.launch(intent)
    }

    private val resultadoGaleria_ARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if (resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imagenUri = data!!.data

                val tiempo = "${Constantes.obtenerTiempoDis()}"
                val modeloImgSel = ModeloImageSeleccionada(
                    tiempo, imagenUri, null, false
                )
                imagenSelecArrayList.add(modeloImgSel)
                cargarImagenes()

            }else{
                Toast.makeText(
                    this,
                    "Cancelado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    private val solicitarPermisocamara = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){resultado->
            var todosConcedidos = true
            for (esConcedido in resultado.values){
                todosConcedidos = todosConcedidos && esConcedido
            }
            if (todosConcedidos){
                imageCamara()

            }else{
                Toast.makeText(
                    this, "El permiso de la cámara o almacenamiento ha sido denegado, o ambos fueron denegados",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    private fun imageCamara() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Titulo_imagen")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Descripcion_imagen")
        imagenUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri)
        resultadoCamara_ARL.launch(intent)
    }

    private val resultadoCamara_ARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if (resultado.resultCode == Activity.RESULT_OK){
                val tiempo = "${Constantes.obtenerTiempoDis()}"
                val modeloImgSel = ModeloImageSeleccionada(
                    tiempo, imagenUri, null, false
                )
                imagenSelecArrayList.add(modeloImgSel)
                cargarImagenes()
            }else{
                Toast.makeText(
                    this,
                    "Cancelado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun cargarImagenes() {
        adaptadorImagenSel = AdaptadorImagenSeleccionada(this, imagenSelecArrayList)
        binding.RVImagenes.adapter = adaptadorImagenSel
    }
}