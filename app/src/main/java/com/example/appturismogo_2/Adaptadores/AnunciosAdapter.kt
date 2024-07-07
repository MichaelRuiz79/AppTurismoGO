package com.example.appturismogo_2.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appturismogo_2.Fragmentos.Anuncio
import com.example.appturismogo_2.R

class AnunciosAdapter(private val context: Context, private val listaAnuncios: List<Anuncio>) :
    RecyclerView.Adapter<AnunciosAdapter.AnuncioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnuncioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_anuncio2, parent, false)
        return AnuncioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnuncioViewHolder, position: Int) {
        val anuncio = listaAnuncios[position]
        holder.bind(anuncio)
    }

    override fun getItemCount(): Int {
        return listaAnuncios.size
    }

    inner class AnuncioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        private val telefonoTextView: TextView = itemView.findViewById(R.id.telefonoTextView)
        private val dniTextView: TextView = itemView.findViewById(R.id.dniTextView)
        private val tourTextView: TextView = itemView.findViewById(R.id.tourTextView)
        private val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        private val puntuacionTextView: TextView = itemView.findViewById(R.id.puntuacionTextView)

        fun bind(anuncio: Anuncio) {
            nombreTextView.text = anuncio.nombre
            telefonoTextView.text = anuncio.telefono
            dniTextView.text = anuncio.dni
            tourTextView.text = anuncio.tour
            fechaTextView.text = anuncio.fecha
            puntuacionTextView.text = anuncio.puntuacion.toString()
        }
    }
}
