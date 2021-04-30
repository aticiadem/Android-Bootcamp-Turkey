package com.aa.harcamalarabt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.ExpenseRecyclerRowBinding
import com.aa.harcamalarabt.model.ExpenseModel
import com.aa.harcamalarabt.ui.fragment.HomeFragmentDirections
import java.text.DecimalFormat

class ExpenseRecyclerAdapter(private val currencyType: Int, private val list: ArrayList<Double>): RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder>() {

    private var expenseList = emptyList<ExpenseModel>()

    class ExpenseViewHolder(val itemBinding: ExpenseRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseRecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return ExpenseViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentItem = expenseList[position]
        val currentPrice = currentItem.priceValue
        holder.itemBinding.textViewTitle.text = currentItem.statement
        when(currentItem.billType){
            1 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.bill)
            2 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.home)
            3 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.shopping_bag)
        }
        when(currencyType){
            1 -> { // veritabanindaki birimlerin tl donusumu
                when(currentItem.currencyType){ // Degerin para birimi
                    1 -> { // TL
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(currentPrice)} TL"
                    }
                    2 -> { // Sterlin tl ceviri
                        // 1 sterlin kac euro? -> x
                        val x = 1/list[1]
                        // x euro kac tl ?
                        val value = x*list[0]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} TL"
                    }
                    3 -> { // Dolar in Tl donusumu
                        val x = 1/list[2]
                        val value = x*list[0]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} TL"
                    }
                    4 -> { // Euro
                        val tl_1_kac_euro = 1*list[0]
                        val total = currentPrice * tl_1_kac_euro
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} TL"
                    }
                }
            }
            2 -> { // veritabanindaki birimlerin sterlin donusumu
                when(currentItem.currencyType){
                    1 -> { // TL
                        val x = 1/list[0]
                        val value = x * list[1]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Sterlin"
                    }
                    2 -> { // Sterlin
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(currentPrice)} Sterlin"
                    }
                    3 -> { // Dolar
                        val x = 1/list[2]
                        val value = x * list[1]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Sterlin"
                    }
                    4 -> { // Euro
                        val sterlin1_kac_euro = 1*list[1]
                        val total = currentPrice * sterlin1_kac_euro
                        /*
                        val x = 1/list[3]
                        val value = x*list[1]
                        val total = currentPrice * value
                       */
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Sterlin"
                    }
                }
            }
            3 -> {
                when(currentItem.currencyType){
                    1 -> { // TL
                        val x = 1/list[0]
                        val value = x * list[2]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Dolar"
                    }
                    2 -> { // Sterlin
                        val x = 1/list[1]
                        val value = x * list[2]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Dolar"
                    }
                    3 -> { // Dolar
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(currentPrice)} Dolar"
                    }
                    4 -> { // Manat
                        val dolar1_kac_euro = 1*list[2]
                        val total = currentPrice * dolar1_kac_euro
                        /*
                        val x = 1/list[3]
                        val value = x * list[2]
                        val total = currentPrice * value */
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Dolar"
                    }
                }
            }
            4 -> {
                when(currentItem.currencyType){
                    1 -> { // TL
                        val x = 1/list[0]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Euro"
                    }
                    2 -> { // Sterlin
                        val x = 1/list[1]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Euro"
                    }
                    3 -> { // Dolar
                        val x = 1/list[2]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Euro"
                    }
                    4 -> { // Manat
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(currentPrice)} Euro"
                    }
                }
            }
        }

        holder.itemBinding.cardView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToExpenseDetailF(currentItem.expenseId)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    fun setData(expense: List<ExpenseModel>){
        this.expenseList = expense
        notifyDataSetChanged()
    }

}