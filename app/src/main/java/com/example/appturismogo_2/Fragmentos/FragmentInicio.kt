package com.example.appturismogo_2.Fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appturismogo_2.Adaptadores.AdaptadorAnuncio
import com.example.appturismogo_2.Adaptadores.AdaptadorCategoria
import com.example.appturismogo_2.Constantes
import com.example.appturismogo_2.Modelo.ModeloAnuncio
import com.example.appturismogo_2.Modelo.ModeloCategoria
import com.example.appturismogo_2.R
import com.example.appturismogo_2.RvListenerCategoria
import com.example.appturismogo_2.databinding.FragmentInicioBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FragmentInicio : Fragment() {

    private lateinit var binding : FragmentInicioBinding

    private lateinit var mContext : Context

    private lateinit var anuncioArrayList: ArrayList<ModeloAnuncio>
    private lateinit var adaptadorAnuncio: AdaptadorAnuncio


    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate(LayoutInflater.from(mContext),container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cargarCategorias()
        cargarAnuncios("Todos")
    }

    private fun cargarCategorias(){
        val categoriaArrayList = ArrayList<ModeloCategoria>()
        for (i in 0 until Constantes.categorias.size){
            val modeloCategoria = ModeloCategoria(Constantes.categorias[i], Constantes.categoriasIcono[i])
            categoriaArrayList.add(modeloCategoria)
        }

        val adaptadorCategoria = AdaptadorCategoria(
            mContext,
            categoriaArrayList,
            object : RvListenerCategoria {
                override fun onCategoriaClick(modeloCategoria: ModeloCategoria) {
                    val categoriaSeleccionada = modeloCategoria.categoria
                    cargarAnuncios(categoriaSeleccionada)

                }
            }
        )

        binding.categoriaRv.adapter = adaptadorCategoria
    }

    private fun cargarAnuncios(categoria: String){
        anuncioArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                anuncioArrayList.clear()
                for (ds in snapshot.children){
                    try{
                        val modeloAnuncio= ds.getValue(ModeloAnuncio::class.java)

                    }catch (e:Exception){


                    }
                }

                adaptadorAnuncio = AdaptadorAnuncio(mContext,anuncioArrayList)
                binding.anunciosRv.adapter = adaptadorAnuncio
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



}

