package com.aa.harcamalarabt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseModel(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int, // id
    @ColumnInfo(name = "statement")
    val statement: String, // aciklama
    @ColumnInfo(name = "priceValue")
    val priceValue: Int, // harcama miktari
    @ColumnInfo(name = "billType")
    val billType: Int, // fatura secimi
    @ColumnInfo(name = "currencyType")
    val currencyType: Int // para birimi
)