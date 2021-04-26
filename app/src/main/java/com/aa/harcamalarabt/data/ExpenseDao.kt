package com.aa.harcamalarabt.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aa.harcamalarabt.model.ExpenseModel

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExpense(expense: ExpenseModel)

    @Query("SELECT * FROM expense_table ORDER BY expenseId DESC") // buyuk veri ilk gelsin
    fun readAllData(): LiveData<List<ExpenseModel>>

}