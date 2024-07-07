package com.example.appturismogo_2.Adaptadores

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismogo_2.Modelo.ModeloCategoria
import com.example.appturismogo_2.RvListenerCategoria
import com.example.appturismogo_2.databinding.ItemCategoriaInicioBinding
import java.util.Random

class AdaptadorCategoria(
    private val context: Context,
    private val categoriaArrayList: ArrayList<ModeloCategoria>,
    private val rvListenerCategoria: RvListenerCategoria
) : RecyclerView.Adapter<AdaptadorCategoria.HolderCategoria>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategoria {
        val binding = ItemCategoriaInicioBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderCategoria(binding)
    }

    override fun getItemCount(): Int {
        return categoriaArrayList.size
    }

    override fun onBindViewHolder(holder: HolderCategoria, position: Int) {
        val modeloCategoria = categoriaArrayList[position]

        val icono = modeloCategoria.icono
        val categoria = modeloCategoria.categoria

        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(255),
            random.nextInt(255),
            random.nextInt(255)
        )

        holder.binding.categoriaIconoIv.setImageResource(icono)
        holder.binding.TvCategoria.text = categoria
        holder.binding.categoriaIconoIv.setBackgroundColor(color)

        holder.binding.root.setOnClickListener {
            rvListenerCategoria.onCategoriaClick(modeloCategoria)
        }
    }

    inner class HolderCategoria(val binding: ItemCategoriaInicioBinding) : RecyclerView.ViewHolder(binding.root)
}
