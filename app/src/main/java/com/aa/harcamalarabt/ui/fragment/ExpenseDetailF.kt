package com.aa.harcamalarabt.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
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
import java.text.DecimalFormat

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

        val sharedPreferences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)
        val lastClickedItem = sharedPreferences.getInt("lastClickedItem",1)

        val dataTl = sharedPreferences.getFloat("tl", 1F)
        val dataSterlin = sharedPreferences.getFloat("sterlin", 1F)
        val dataDolar = sharedPreferences.getFloat("dolar", 1F)
        val dataManat = sharedPreferences.getFloat("manat", 1F)

        val list = ArrayList<Double>()
        list.clear()
        list.add(dataTl.toDouble())
        list.add(dataSterlin.toDouble())
        list.add(dataDolar.toDouble())
        list.add(dataManat.toDouble())

        when(lastClickedItem){
            1 -> { // TL basilmis
                when(currentModel.currencyType){
                    1 -> { // Degerin Tl Donusumu
                        binding.textViewPrice.text = "${currentModel.priceValue} TL"
                    }
                    2 -> { // Sterlin tl ceviri
                        val x = 1/list[1]
                        // x euro kac tl ?
                        val value = x*list[0]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total TL"
                    }
                    3 -> { // Dolar in Tl donusumu
                        val x = 1/list[2]
                        val value = x*list[0]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total TL"
                    }
                    4 -> { // Euro
                        val tl_1_kac_euro = 1*list[0]
                        val total = currentModel.priceValue * tl_1_kac_euro
                        binding.textViewPrice.text = "$total TL"
                    }
                }
            }
            2 -> { // veritabanindaki birimlerin sterlin donusumu
                when(currentModel.currencyType){
                    1 -> { // TL
                        val x = 1/list[0]
                        val value = x * list[1]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Sterlin"
                    }
                    2 -> { // Sterlin
                        binding.textViewPrice.text = "${currentModel.priceValue} Sterlin"
                    }
                    3 -> { // Dolar
                        val x = 1/list[2]
                        val value = x * list[1]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Sterlin"
                    }
                    4 -> { // Euro
                        val sterlin1_kac_euro = 1*list[1]
                        val total = currentModel.priceValue * sterlin1_kac_euro
                        binding.textViewPrice.text = "$total Sterlin"
                    }
                }
            }
            3 -> {
                when(currentModel.currencyType){
                    1 -> { // TL
                        val x = 1/list[0]
                        val value = x * list[2]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Dolar"
                    }
                    2 -> { // Sterlin
                        val x = 1/list[1]
                        val value = x * list[2]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Dolar"
                    }
                    3 -> { // Dolar
                        binding.textViewPrice.text = "${currentModel.priceValue} Dolar"
                    }
                    4 -> { // Manat
                        val dolar1_kac_euro = 1*list[2]
                        val total = currentModel.priceValue * dolar1_kac_euro
                        binding.textViewPrice.text = "$total Dolar"
                    }
                }
            }
            4 -> {
                when(currentModel.currencyType){
                    1 -> { // TL
                        val x = 1/list[0]
                        val value = x * list[3]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Euro"
                    }
                    2 -> { // Sterlin
                        val x = 1/list[1]
                        val value = x * list[3]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Euro"
                    }
                    3 -> { // Dolar
                        val x = 1/list[2]
                        val value = x * list[3]
                        val total = currentModel.priceValue * value
                        binding.textViewPrice.text = "$total Euro"
                    }
                    4 -> { // Manat
                        binding.textViewPrice.text = "${currentModel.priceValue} Euro"
                    }
                }
            }
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