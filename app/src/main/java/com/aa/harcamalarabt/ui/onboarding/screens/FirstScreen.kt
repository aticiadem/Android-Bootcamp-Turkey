package com.aa.harcamalarabt.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstScreenBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.lottie.setAnimation("firstscreenanimation.json")
        binding.lottie.playAnimation()

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.buttonNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}