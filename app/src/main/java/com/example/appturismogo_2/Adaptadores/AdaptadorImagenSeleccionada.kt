package com.example.appturismogo_2.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.appturismogo_2.Modelo.ModeloImageSeleccionada
import com.example.appturismogo_2.R
import com.example.appturismogo_2.databinding.ItemImagenesSeleccionadasBinding

class AdaptadorImagenSeleccionada(
    private val context: Context,
    private val imagenesSelecArrayList: ArrayList<ModeloImageSeleccionada>
): RecyclerView.Adapter<AdaptadorImagenSeleccionada.HolderImagenSeleccionada>() {
    private lateinit var binding : ItemImagenesSeleccionadasBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImagenSeleccionada {
        binding = ItemImagenesSeleccionadasBinding.inflate(LayoutInflater.from(context), parent,false)
        return HolderImagenSeleccionada(binding.root)
    }

    override fun getItemCount(): Int {
        return imagenesSelecArrayList.size
    }

    override fun onBindViewHolder(holder: HolderImagenSeleccionada, position: Int) {
        val modelo = imagenesSelecArrayList[position]
        val imagenUri = modelo.imagenUri
        try {

            Glide.with(context)
                .load(imagenUri)
                .placeholder(R.drawable.item_imagen)
                .into(holder.item_imagen)
        }catch (e:Exception){

        }

        holder.btn_cerrar.setOnClickListener{
            imagenesSelecArrayList.remove(modelo)
            notifyDataSetChanged()
        }
    }

    inner class HolderImagenSeleccionada (itemView: View): ViewHolder(itemView){
        var item_imagen = binding.ItemImagen
        var btn_cerrar = binding.cerrarItem

    }



}