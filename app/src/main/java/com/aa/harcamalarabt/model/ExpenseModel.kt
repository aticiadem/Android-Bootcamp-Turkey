package com.aa.harcamalarabt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseModel(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int,
    val statement: String,
    val priceValue: Int,
    val billType: Int,
    val currencyType: Int
)