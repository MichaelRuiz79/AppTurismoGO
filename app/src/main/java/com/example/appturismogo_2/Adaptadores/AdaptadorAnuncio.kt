package com.example.appturismogo_2.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appturismogo_2.Constantes
import com.example.appturismogo_2.Modelo.ModeloAnuncio
import com.example.appturismogo_2.R
import com.example.appturismogo_2.databinding.ItemAnuncioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdaptadorAnuncio: RecyclerView.Adapter<AdaptadorAnuncio.HolderAnuncio> {

    private lateinit var binding: ItemAnuncioBinding
    private var context:Context
    private var anuncioArrayList : ArrayList<ModeloAnuncio>
    private var firebaseAuth : FirebaseAuth

    constructor(context: Context, anuncioArrayList: ArrayList<ModeloAnuncio>) {
        this.context = context
        this.anuncioArrayList = anuncioArrayList
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HolderAnuncio {
        binding = ItemAnuncioBinding.inflate(LayoutInflater
            .from(context),parent,false)
        return HolderAnuncio(binding.root)
    }

    override fun getItemCount(): Int {
        return anuncioArrayList.size
    }

    override fun onBindViewHolder(holder: HolderAnuncio, position: Int) {
        val modeloAnuncio = anuncioArrayList[position]
        val titulo = modeloAnuncio.titulo_anuncio
        val descripcion = modeloAnuncio.descripcion
        val direccion = modeloAnuncio.direccion
        val precio = modeloAnuncio.precio
        val tiempo = modeloAnuncio.tiempo

        val formatoFecha = Constantes.obtenerFecha(tiempo)

        cargarPrimeraImgAnuncio(modeloAnuncio,holder)
        comprobarFavorito(modeloAnuncio, holder)

        holder.tv_titulo.text = titulo
        holder.Tv_descripcion.text = descripcion
        holder.Tv_direccion.text = direccion
        holder.tv_precio.text = precio
        holder.tv_fecha.text = formatoFecha

        holder.tv_fav.setOnClickListener {
            val favorito = modeloAnuncio.favorito

            if (favorito){
                //Favorito = true
                Constantes.eliminarAnuncioFav(context, modeloAnuncio.id)
            }else{
                //Favorito = false
                Constantes.agregarAnuncioFav(context, modeloAnuncio.id)
            }
        }

    }

    private fun comprobarFavorito(modeloAnuncio: ModeloAnuncio, holder: AdaptadorAnuncio.HolderAnuncio) {
        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!).child("Favoritos").child(modeloAnuncio.id)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val favorito = snapshot.exists()
                    modeloAnuncio.favorito = favorito

                    if (favorito){
                        holder.tv_fav.setImageResource(R.drawable.ic_anuncio_es_favorito)
                    }else{
                        holder.tv_fav.setImageResource(R.drawable.ic_no_favorito)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }


    private fun cargarPrimeraImgAnuncio(modeloAnuncio: ModeloAnuncio, holder: AdaptadorAnuncio.HolderAnuncio) {

        val idAnuncio = modeloAnuncio.id

        val ref = FirebaseDatabase.getInstance().getReference("Anuncios")
        ref.child(idAnuncio).child("Imagenes").limitToFirst(1)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(ds in snapshot.children){
                        val imagenUrl = "${ds.child("imageUrl").value}"
                        try{
                            Glide.with(context)
                                .load(imagenUrl)
                                .placeholder(R.drawable.ic_imagen)
                                .into(holder.imagenIv)
                        }catch (e:Exception){

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }


    inner class HolderAnuncio(itemView:View):RecyclerView.ViewHolder(itemView){

        var imagenIv = binding.imagenIv
        var tv_titulo = binding.TvTitulo
        var Tv_descripcion = binding.TvDescripcion
        var Tv_direccion = binding.TvDireccion
        var tv_precio = binding.TvPrecio
        var tv_fecha = binding.TvFecha
        var tv_fav = binding.IbFav

    }



}