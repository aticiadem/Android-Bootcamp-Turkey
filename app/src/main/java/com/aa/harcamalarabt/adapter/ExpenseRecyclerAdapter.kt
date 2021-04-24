package com.aa.harcamalarabt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aa.harcamalarabt.databinding.ExpenseRecyclerRowBinding
import com.aa.harcamalarabt.model.ExpenseModel

class ExpenseRecyclerAdapter(private val expenseList: ArrayList<ExpenseModel>): RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder>() {

    class ExpenseViewHolder(val itemBinding: ExpenseRecyclerRowBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseRecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.itemBinding.textViewTitle.text = expenseList[position].expenseTitle
        holder.itemBinding.textViewPrice.text = expenseList[position].expensePrice
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

}