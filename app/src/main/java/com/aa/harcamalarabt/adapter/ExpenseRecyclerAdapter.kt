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

class ExpenseRecyclerAdapter(val currencyType: Int, val list: ArrayList<Double>): RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder>() {

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
            1 -> {
                when(currentItem.currencyType){ // veritabanindaki birimlerin tl donusumu
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
                    3 -> { // Dolar
                        val x = 1/list[2]
                        val value = x*list[0]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} TL"
                    }
                    4 -> { // Manat
                        val x = 1/list[3]
                        val value = x*list[0]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} TL"
                    }
                }
            }
            2 -> {
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
                    4 -> { // Manat
                        val x = 1/list[3]
                        val value = x*list[1]
                        val total = currentPrice * value
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
                        val x = 1/list[3]
                        val value = x * list[2]
                        val total = currentPrice * value
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
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Manat"
                    }
                    2 -> { // Sterlin
                        val x = 1/list[1]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Manat"
                    }
                    3 -> { // Dolar
                        val x = 1/list[2]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(total)} Manat"
                    }
                    4 -> { // Manat
                        holder.itemBinding.textViewPrice.text = "${DecimalFormat("##.#").format(currentPrice)} Manat"
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