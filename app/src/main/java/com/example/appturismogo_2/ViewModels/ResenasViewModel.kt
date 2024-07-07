package com.example.appturismogo_2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appturismogo_2.Modelo.ModeloResena
import com.google.firebase.firestore.FirebaseFirestore

class ResenasViewModel : ViewModel() {

    private val _resenas = MutableLiveData<List<ModeloResena>>()
    val resenas: LiveData<List<ModeloResena>> get() = _resenas

    init {
        // Cargar reseñas desde la base de datos
        FirebaseFirestore.getInstance().collection("resenas")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val resenasList = it.toObjects(ModeloResena::class.java)
                    _resenas.value = resenasList
                }
            }
    }
}
