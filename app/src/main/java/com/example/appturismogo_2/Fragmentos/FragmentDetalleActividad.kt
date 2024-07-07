package com.example.appturismogo_2.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.appturismogo_2.databinding.FragmentDetalleActividadBinding

class FragmentDetalleActividad : Fragment() {

    private var _binding: FragmentDetalleActividadBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentDetalleActividadArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleActividadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actividad = args.actividad
        binding.textViewNombre.text = actividad.nombre
        binding.textViewDescripcion.text = actividad.descripcion
        binding.textViewItinerario.text = actividad.itinerario
        binding.buttonReservar.setOnClickListener {
            // Lógica para reservar la actividad
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
