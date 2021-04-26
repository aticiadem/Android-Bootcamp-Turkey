package com.aa.harcamalarabt.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.data.ExpenseDatabase
import com.aa.harcamalarabt.databinding.FragmentAddExpenseBinding
import com.aa.harcamalarabt.model.ExpenseModel

class AddExpenseF : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: ExpenseDatabase

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

        db = ExpenseDatabase.getDatabase(requireContext())

        var expenseValue: Int? = null
        var currencyType: Int? = null

        binding.radioGroupExpense.setOnCheckedChangeListener { group, checkedId ->
            expenseValue = when(checkedId){
                R.id.radioButtonBill -> 1 // Fatura
                R.id.radioButtonRent -> 2 // Kira
                else -> 3 // Diger
            }
        }

        binding.radioGroupUnit.setOnCheckedChangeListener { group, checkedId ->
            currencyType = when(checkedId){
                R.id.radioButtonTl -> 1 // Tl
                R.id.radioButtonSterlin -> 2 // Sterlin
                R.id.radioButtonDolar -> 3 // Dolar
                else -> 4 // Euro
            }
        }

        binding.buttonAdd.setOnClickListener {
            if (binding.editTextPriceValue.text.isNotEmpty() && binding.radioGroupExpense.checkedRadioButtonId != -1
                    && binding.editTextStatement.text.isNotEmpty() && binding.radioGroupUnit.checkedRadioButtonId != -1){
                // Veri Kaydi
                val statement = binding.editTextStatement.text.toString()
                val priceValue = binding.editTextPriceValue.text.toString().toInt()
                insertData(statement,priceValue,expenseValue!!,currencyType!!)
                findNavController().navigate(R.id.action_addExpenseF_to_homeFragment)
                Toast.makeText(requireContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),"Lütfen Bütün Alanları Doldurunuz",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertData(statement: String, priceValue: Int, billType: Int, currencyType: Int){
        val expense = ExpenseModel(0,statement,priceValue,billType,currencyType)
        db.expenseDao().addExpense(expense)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}