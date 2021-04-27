package com.aa.harcamalarabt.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.data.ExpenseDatabase
import com.aa.harcamalarabt.databinding.FragmentExpenseDetailBinding

class ExpenseDetailF : Fragment() {

    private var _binding: FragmentExpenseDetailBinding? = null
    private val binding get() = _binding!!
    val args: ExpenseDetailFArgs by navArgs()
    private lateinit var db: ExpenseDatabase

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseDetailBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = ExpenseDatabase.getDatabase(requireContext())

        val currentModel = db.expenseDao().getExpense(args.expenseId)

        binding.imageView.setImageResource(
                when(currentModel.billType){
                    1 -> R.drawable.bill
                    2 -> R.drawable.home
                    else -> R.drawable.shopping_bag
                }
        )

        when(currentModel.currencyType){
            1 -> binding.textViewPrice.text = "${currentModel.priceValue} TL"
            2 -> binding.textViewPrice.text = "${currentModel.priceValue} Sterlin"
            3 -> binding.textViewPrice.text = "${currentModel.priceValue} Dolar"
            4 -> binding.textViewPrice.text = "${currentModel.priceValue} Euro"
        }

        binding.textViewExpense.text = currentModel.statement

        binding.buttonDelete.setOnClickListener {
            deleteExpense()
            findNavController().navigate(R.id.action_expenseDetailF_to_homeFragment2)
            Toast.makeText(requireContext(),"Silme İşlemi Başarılı",Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteExpense(){
        val x = db.expenseDao().getExpense(args.expenseId)
        db.expenseDao().deleteExpense(x)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}