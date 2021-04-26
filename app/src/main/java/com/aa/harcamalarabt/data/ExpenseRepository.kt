package com.aa.harcamalarabt.data

import androidx.lifecycle.LiveData
import com.aa.harcamalarabt.model.ExpenseModel

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val readAllData: LiveData<List<ExpenseModel>> = expenseDao.readAllData()

    suspend fun addExpense(expense: ExpenseModel){
        expenseDao.addExpense(expense)
    }

}