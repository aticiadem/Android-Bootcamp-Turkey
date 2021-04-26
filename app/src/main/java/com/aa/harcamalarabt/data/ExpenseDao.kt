package com.aa.harcamalarabt.data

import androidx.room.*
import com.aa.harcamalarabt.model.ExpenseModel

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addExpense(expense: ExpenseModel)

    @Query("SELECT * FROM expense_table ORDER BY expenseId DESC") // buyuk veri ilk gelsin
    fun readAllData(): List<ExpenseModel>

    @Delete
    fun deleteExpense(expense: ExpenseModel)

    @Query("SELECT * FROM expense_table WHERE expenseId = :expenseId")
    fun getExpense(expenseId: Int): ExpenseModel

}