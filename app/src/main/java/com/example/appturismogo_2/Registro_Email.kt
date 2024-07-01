package com.example.appturismogo_2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appturismogo_2.databinding.ActivityCrearAnuncioBinding
import com.example.appturismogo_2.databinding.ActivityRegistroEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registro_Email : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear Instancia de Firebase autentication
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnRegistrar.setOnClickListener{
            validarinfo()
        }

    }

    // String para almacenar información ingresada por el usuario

    private var email = ""
    private var password = ""
    private var repeat_password = ""
    private var NombreCompleto = ""
    private var Identificacion = ""

    private fun validarinfo(){
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()
        repeat_password = binding.EtRepeatPassword.text.toString().trim()
        NombreCompleto = binding.EtNombreUsuario.text.toString()
        Identificacion = binding.EtDNIRUC.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmail.error = "Email invalido"
            binding.EtEmail.requestFocus()
        }
        else if(email.isEmpty()){
            binding.EtEmail.error="Ingrese email"
            binding.EtEmail.requestFocus()
        }
        else if (password.isEmpty()){
            binding.EtPassword.error= "Ingrese Contraseña"
            binding.EtPassword.requestFocus()
        }
        else if (repeat_password.isEmpty()){
            binding.EtPassword.error="Repita su contraseña"
            binding.EtRepeatPassword.requestFocus()
        }

        else if (password != repeat_password) {
            binding.EtRepeatPassword.error = "Las contraseñas ingresadas no son iguales"
            binding.EtRepeatPassword.requestFocus()
        }
        else if (Identificacion.length<8) {
            binding.EtDNIRUC.error = "Ingrese un número de identificación válido"
            binding.EtDNIRUC.requestFocus()
        }
        else if (NombreCompleto.isEmpty()){
            binding.EtNombreUsuario.error="Ingrese s nombre completo"
            binding.EtNombreUsuario.requestFocus()
        }

        else{
            registrarusuario()
        }
    }

    private fun registrarusuario() {
        progressDialog.setMessage("Creando su Cuenta")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                llenarInfoBD()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se realizó el registro debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
    }

    private fun llenarInfoBD(){
        progressDialog.setMessage("Guardando Informacion")

        val tiempo = Constantes.obtenertiempodevice()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario = firebaseAuth.uid
        val Nombre_RazonSocial = NombreCompleto
        val NumIdentificacion = Identificacion

        val hashMap = HashMap <String, Any>()
        hashMap ["proveedor"] = "Email"
        hashMap ["tiempo"]= tiempo
        //hashMap ["cod.telefono"]= ""
        //hashMap ["telefono"]= ""
        //hashMap ["online"]= true
        //hashMap ["escribiendo"] = ""
        //hashMap ["urlImagePerfil"] = ""
        hashMap["email"]= "${emailUsuario}"
        hashMap ["uid"]= "${uidUsuario}"
        hashMap ["Nombre_RazonSocial"]= "${Nombre_RazonSocial}"
        hashMap ["DNI_RUC"]= "${NumIdentificacion}"


        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se registró al usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

}