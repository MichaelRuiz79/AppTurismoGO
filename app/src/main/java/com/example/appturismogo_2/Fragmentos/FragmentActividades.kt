package com.example.appturismogo_2.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appturismogo_2.Adaptadores.AdaptadorActividades
import com.example.appturismogo_2.databinding.FragmentActividadesBinding
import com.example.appturismogo_2.viewmodels.ActividadesViewModel

class FragmentActividades : Fragment() {

    private var _binding: FragmentActividadesBinding? = null
    private val binding get() = _binding!!
    private lateinit var actividadesAdapter: AdaptadorActividades
    private val viewModel: ActividadesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActividadesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actividadesAdapter = AdaptadorActividades {
            val action = FragmentActividadesDirections.actionActividadesFragmentToDetalleActividadFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = actividadesAdapter
        }

        viewModel.actividades.observe(viewLifecycleOwner) { actividades ->
            actividadesAdapter.submitList(actividades)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
