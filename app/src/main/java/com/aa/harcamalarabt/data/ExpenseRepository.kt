package com.aa.harcamalarabt.data

import com.aa.harcamalarabt.model.ExpenseModel

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    fun addExpense(expense: ExpenseModel){
        expenseDao.addExpense(expense)
    }

    fun readAllData(): List<ExpenseModel>{
        return expenseDao.readAllData()
    }

    fun deleteExpense(expense: ExpenseModel){
        expenseDao.deleteExpense(expense)
    }

    fun getExpense(expenseId: Int): ExpenseModel{
        return expenseDao.getExpense(expenseId)
    }

}