package com.example.appturismogo_2.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.appturismogo_2.databinding.FragmentResenaBinding
import com.google.firebase.firestore.FirebaseFirestore

class FragmentResena : Fragment() {

    private var _binding: FragmentResenaBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentResenaArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResenaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actividad = args.actividad

        binding.buttonSubmit.setOnClickListener {
            val rating = binding.ratingBar.rating
            val comentario = binding.editTextComentario.text.toString().trim()

            if (comentario.isEmpty()) {
                binding.editTextComentario.error = "Ingrese un comentario"
                return@setOnClickListener
            }

            val resena = Resena(
                actividadId = actividad.id,
                rating = rating,
                comentario = comentario
            )

            FirebaseFirestore.getInstance().collection("resenas")
                .add(resena)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Reseña guardada", Toast.LENGTH_SHORT).show()
                    binding.ratingBar.rating = 0f
                    binding.editTextComentario.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error al guardar la reseña", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
