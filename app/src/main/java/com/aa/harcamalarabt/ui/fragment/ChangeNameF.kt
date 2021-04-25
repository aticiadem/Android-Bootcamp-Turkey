package com.aa.harcamalarabt.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.navigation.Navigation
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.FragmentChangeNameBinding

class ChangeNameF : Fragment() {

    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeNameBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var gender: Int? = null

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            gender = when (checkedId) {
                R.id.radioButtonMan -> {
                    1
                }
                R.id.radioButtonWomen -> {
                    2
                }
                else -> {
                    3
                }
            }
        }

        binding.buttonSave.setOnClickListener {
            if(binding.editTextUserName.text.isNotEmpty() && binding.radioGroup.checkedRadioButtonId != -1){
                val userName = binding.editTextUserName.text.toString()

                val sharedPreferences = requireActivity().getSharedPreferences("Name",
                    Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("name",userName)
                editor.putInt("gender",gender!!)
                editor.apply()

                val action = ChangeNameFDirections.actionChangeNameFToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                Toast.makeText(requireActivity(),"Lütfen Bütün Alanları Doldurunuz!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}