package com.aa.harcamalarabt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))

        binding.buttonDolar.setOnClickListener {
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

        binding.buttonSterlin.setOnClickListener {
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

        binding.buttonEuro.setOnClickListener {
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

        binding.buttonTl.setOnClickListener {
            binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonDolar.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonSterlin.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
            binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_gray))
        }

    }

}