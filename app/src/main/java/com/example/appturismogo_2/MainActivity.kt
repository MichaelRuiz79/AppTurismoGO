package com.example.appturismogo_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appturismogo_2.Anuncios.CrearAnuncio
import com.example.appturismogo_2.Fragmentos.*
import com.example.appturismogo_2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        // Configuración del BottomNavigationView
        binding.BottomNV.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_Inicio -> {
                    verFragmentInicio()
                    true
                }
                R.id.Item_Chat -> {
                    verFragmentChat()
                    true
                }
                R.id.Item_Anuncios -> {
                    verFragmentAnuncios()
                    true
                }
                R.id.Item_Cuenta -> {
                    verFragmentCuenta()
                    true
                }
                R.id.Item_Actividades -> {
                    verFragmentActividades()
                    true
                }
                R.id.Item_Resenas -> {
                    verFragmentResenas()
                    true
                }
                R.id.Item_DejarResena -> {
                    verFragmentDejarResena()
                    true
                }
                else -> {
                    false
                }
            }
        }

        binding.FAB.setOnClickListener {
            startActivity(Intent(this, CrearAnuncio::class.java))
        }
    }

    private fun comprobarSesion() {
        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, Opciones_Login::class.java))
            finishAffinity()
        }
    }

    private fun verFragmentInicio() {
        binding.TituloRL.text = "Inicio"
        val fragment = FragmentInicio()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentInicio")
        fragmentTransaction.commit()
    }

    private fun verFragmentAnuncios() {
        binding.TituloRL.text = "Anuncios"
        val fragment = FragmentMisAnuncios()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentAnuncios")
        fragmentTransaction.commit()
    }

    private fun verFragmentChat() {
        binding.TituloRL.text = "Chat"
        val fragment = FragmentChat()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentChat")
        fragmentTransaction.commit()
    }

    private fun verFragmentCuenta() {
        binding.TituloRL.text = "Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentCuenta")
        fragmentTransaction.commit()
    }

    private fun verFragmentActividades() {
        binding.TituloRL.text = "Actividades"
        val fragment = FragmentActividades()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentActividades")
        fragmentTransaction.commit()
    }

    private fun verFragmentResenas() {
        binding.TituloRL.text = "Reseñas"
        val fragment = FragmentResenas()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentResenas")
        fragmentTransaction.commit()
    }

    private fun verFragmentDejarResena() {
        binding.TituloRL.text = "Dejar Reseña"
        val fragment = FragmentResena()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentL1.id, fragment, "FragmentDejarResena")
        fragmentTransaction.commit()
    }
}
