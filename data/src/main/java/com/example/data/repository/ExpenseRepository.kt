package com.example.data.repository

import com.example.data.local.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun upsertExpense(expense: Expense)
    suspend fun deleteExpense(id: Int)
    fun getAllExpenses(): Flow<List<Expense>>
}