package com.aa.harcamalarabt.model

data class ExpenseModel(
    val expenseId: Int,
    val statement: String,
    val priceValue: Int,
    val billType: Int,
    val currencyType: Int
)