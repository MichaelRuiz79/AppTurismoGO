package com.example.appturismogo_2.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appturismogo_2.Adaptadores.AdaptadorResenas
import com.example.appturismogo_2.databinding.FragmentResenasBinding
import com.example.appturismogo_2.viewmodels.ResenasViewModel

class FragmentResenas : Fragment() {

    private var _binding: FragmentResenasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ResenasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResenasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resenasAdapter = AdaptadorResenas()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resenasAdapter
        }

        viewModel.resenas.observe(viewLifecycleOwner, {
            resenasAdapter.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
