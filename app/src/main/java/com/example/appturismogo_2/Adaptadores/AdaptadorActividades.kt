package com.example.appturismogo_2.Adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismogo_2.Modelo.ModeloActividad
import com.example.appturismogo_2.databinding.ItemActividadBinding

class AdaptadorActividades(private val onClick: (ModeloActividad) -> Unit) :
    ListAdapter<ModeloActividad, AdaptadorActividades.ActividadViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ModeloActividad>() {
        override fun areItemsTheSame(oldItem: ModeloActividad, newItem: ModeloActividad): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ModeloActividad, newItem: ModeloActividad): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActividadViewHolder {
        val binding = ItemActividadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActividadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActividadViewHolder, position: Int) {
        val actividad = getItem(position)
        holder.bind(actividad, onClick)
    }

    class ActividadViewHolder(private val binding: ItemActividadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(actividad: ModeloActividad, onClick: (ModeloActividad) -> Unit) {
            binding.apply {
                textViewNombre.text = actividad.nombre
                textViewDescripcion.text = actividad.descripcion
                binding.root.setOnClickListener { onClick(actividad) }
            }
        }
    }
}
