package com.example.data.local.mapper

import com.example.data.local.model.Expense
import com.example.data.local.model.ExpenseEntity


fun ExpenseEntity.toDomain(): Expense = Expense(id = id, title, amount, entryType, date)
fun Expense.toEntity(): ExpenseEntity = ExpenseEntity(id, title, amount, entryType, date)
