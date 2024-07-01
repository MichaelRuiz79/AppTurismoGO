package com.example.appturismogo_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appturismogo_2.Anuncios.CrearAnuncio
import com.example.appturismogo_2.Fragmentos.FragmentChat
import com.example.appturismogo_2.Fragmentos.FragmentCuenta
import com.example.appturismogo_2.Fragmentos.FragmentInicio
import com.example.appturismogo_2.Fragmentos.FragmentMisAnuncios
import com.example.appturismogo_2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()


        verFragmentInicio()
        binding.BottomNV.setOnNavigationItemSelectedListener {
            item-> when (item.itemId){
                R.id.Item_Inicio->{
                    verFragmentInicio()
                    true
                }
                R.id.Item_Chat->{
                    verFragmentChat()
                    true
                }
                R.id.Item_Anuncios->{
                    verFragmentAnuncios()
                    true
                }
                R.id.Item_Cuenta->{
                    verFragmentCuenta()
                    true
                }
            else->{
                false
            }
            }
        }
        binding.FAB.setOnClickListener{
            startActivity(Intent(this, CrearAnuncio::class.java))
        }

    }

    private fun comprobarSesion(){
        if (firebaseAuth.currentUser == null){
            startActivity(Intent(this,Opciones_Login::class.java))
            finishAffinity()
        }
    }

    private fun verFragmentInicio(){
        binding.TituloRL.text = "Inicio"
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentInicio")
        fragmentTransition.commit()

    }
    private fun verFragmentAnuncios(){
        binding.TituloRL.text = "Anuncios"
        val fragment = FragmentMisAnuncios()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentAnuncios")
        fragmentTransition.commit()

    }
    private fun verFragmentChat(){
        binding.TituloRL.text = "Chat"
        val fragment = FragmentChat()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentChat")
        fragmentTransition.commit()

    }
    private fun verFragmentCuenta(){
        binding.TituloRL.text = "Cuenta"
        val fragment = FragmentCuenta()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.FragmentL1.id,fragment,"FragmentCuenta")
        fragmentTransition.commit()

    }
}

