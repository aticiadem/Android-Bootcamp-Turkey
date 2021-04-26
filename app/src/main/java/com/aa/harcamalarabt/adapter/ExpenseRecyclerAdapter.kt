package com.aa.harcamalarabt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aa.harcamalarabt.R
import com.aa.harcamalarabt.databinding.ExpenseRecyclerRowBinding
import com.aa.harcamalarabt.model.CurrencyModel
import com.aa.harcamalarabt.model.ExpenseModel
import com.aa.harcamalarabt.ui.fragment.HomeFragmentDirections

class ExpenseRecyclerAdapter(): RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder>() {

    private var expenseList = emptyList<ExpenseModel>()

    class ExpenseViewHolder(val itemBinding: ExpenseRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseRecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentItem = expenseList[position]
        holder.itemBinding.textViewTitle.text = currentItem.statement
        holder.itemBinding.textViewPrice.text = currentItem.priceValue.toString()
        when(currentItem.billType){
            1 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.bill)
            2 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.home)
            3 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.shopping_bag)
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