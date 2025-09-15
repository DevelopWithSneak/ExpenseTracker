package com.example.data.repository.impl
import com.example.data.local.dao.ExpenseDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toEntity
import com.example.data.local.model.Expense
import com.example.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override suspend fun upsertExpense(expense: Expense) {
        dao.upsertExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(id: Int) {
        dao.deleteExpense(id)
    }

    override fun getAllExpenses(): Flow<List<Expense>> {
        return dao.getAllExpenses().map { list -> list.map { it.toDomain() } }
    }
}
