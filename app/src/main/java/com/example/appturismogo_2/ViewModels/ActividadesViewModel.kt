package com.example.appturismogo_2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appturismogo_2.Modelo.ModeloActividad
import com.google.firebase.firestore.FirebaseFirestore

class ActividadesViewModel : ViewModel() {

    private val _actividades = MutableLiveData<List<ModeloActividad>>()
    val actividades: LiveData<List<ModeloActividad>> get() = _actividades

    init {
        // Cargar actividades desde la base de datos
        FirebaseFirestore.getInstance().collection("actividades")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val actividadesList = it.toObjects(ModeloActividad::class.java)
                    _actividades.value = actividadesList
                }
            }
    }
}
