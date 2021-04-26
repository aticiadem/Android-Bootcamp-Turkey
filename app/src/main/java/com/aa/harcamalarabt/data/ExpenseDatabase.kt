package com.aa.harcamalarabt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aa.harcamalarabt.model.ExpenseModel

@Database(entities = [ExpenseModel::class], version = 1)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object{
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        context,
                        ExpenseDatabase::class.java,
                        "expense_table"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE as ExpenseDatabase
        }
    }

}