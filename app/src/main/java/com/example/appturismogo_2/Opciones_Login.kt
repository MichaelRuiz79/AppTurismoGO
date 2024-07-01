package com.example.appturismogo_2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appturismogo_2.OpcionesDeLogin.Login_Email
import com.example.appturismogo_2.Opciones_Login
import com.example.appturismogo_2.databinding.ActivityOpcionesLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Opciones_Login : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()


        binding.IngresarEmail.setOnClickListener{
            startActivity(Intent(this@Opciones_Login, Login_Email::class.java))
        }

        binding.IngresarGoogle.setOnClickListener {
            Toast.makeText(
                this,"Esta opción no esta disponible por el momento." +
                        "Por favor, ingrese con Email",
                Toast.LENGTH_SHORT).show()
        }

    }

    private fun comprobarSesion() {
        if(firebaseAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }


}