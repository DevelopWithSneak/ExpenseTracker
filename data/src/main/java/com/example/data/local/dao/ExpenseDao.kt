package com.example.data.local.dao
import androidx.room.*
import com.example.data.local.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Upsert
    suspend fun upsertExpense(expense: ExpenseEntity)

    @Query("delete from  expenses where id=:id")
    suspend fun deleteExpense(id: Int)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
}
