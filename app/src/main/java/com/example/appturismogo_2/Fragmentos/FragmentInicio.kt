package com.example.appturismogo_2.Fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appturismogo_2.Adaptadores.AdaptadorCategoria
import com.example.appturismogo_2.Constantes
import com.example.appturismogo_2.Modelo.ModeloCategoria
import com.example.appturismogo_2.R
import com.example.appturismogo_2.RvListenerCategoria
import com.example.appturismogo_2.databinding.FragmentInicioBinding


class FragmentInicio : Fragment() {

    private lateinit var binding : FragmentInicioBinding
    private lateinit var mContext : Context

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

                }
            }
        )

        binding.categoriaRv.adapter = adaptadorCategoria
    }



}