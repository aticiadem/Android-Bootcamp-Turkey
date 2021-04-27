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

class ExpenseRecyclerAdapter(val currencyType: Int): RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder>() {

    private var expenseList = emptyList<ExpenseModel>()

    class ExpenseViewHolder(val itemBinding: ExpenseRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseRecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return ExpenseViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentItem = expenseList[position]
        holder.itemBinding.textViewTitle.text = currentItem.statement
        when(currentItem.billType){
            1 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.bill)
            2 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.home)
            3 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.shopping_bag)
        }
        when(currencyType){
            1 -> holder.itemBinding.textViewPrice.text = "${currentItem.priceValue} TL"
            2 -> holder.itemBinding.textViewPrice.text = "${currentItem.priceValue} Sterlin"
            3 -> holder.itemBinding.textViewPrice.text = "${currentItem.priceValue} Dolar"
            4 -> holder.itemBinding.textViewPrice.text = "${currentItem.priceValue} Euro"
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