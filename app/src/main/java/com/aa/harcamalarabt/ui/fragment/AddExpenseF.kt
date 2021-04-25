package com.aa.harcamalarabt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.FragmentAddExpenseBinding

class AddExpenseF : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            if (binding.editTextExpense.text.isNotEmpty() && binding.radioGroupExpense.checkedRadioButtonId != -1
                    && binding.editTextStatement.text.isNotEmpty() && binding.radioGroupUnit.checkedRadioButtonId != -1){
                val action = AddExpenseFDirections.actionAddExpenseFToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            } else {

                Toast.makeText(requireContext(),"Lütfen Bütün Alanları Doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}