package com.example.appturismogo_2.Adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismogo_2.Modelo.ModeloResena
import com.example.appturismogo_2.databinding.ItemResenaBinding

class AdaptadorResenas :
    ListAdapter<ModeloResena, AdaptadorResenas.ResenaViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ModeloResena>() {
        override fun areItemsTheSame(oldItem: ModeloResena, newItem: ModeloResena): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ModeloResena, newItem: ModeloResena): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResenaViewHolder {
        val binding = ItemResenaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResenaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResenaViewHolder, position: Int) {
        val resena = getItem(position)
        holder.bind(resena)
    }

    class ResenaViewHolder(private val binding: ItemResenaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resena: ModeloResena) {
            binding.apply {
                ratingBar.rating = resena.rating
                textViewComentario.text = resena.comentario
            }
        }
    }
}
