package com.example.presentation.activities.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.model.EntryType
import com.example.data.local.model.Expense
import com.example.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainState(
    val itemsList: List<Expense> = emptyList(),
    val totalIncome: String = "0",
    val totalExpense: String = "0",
    val totalBalance: String = "0"
)

class MainViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        getAllData()
    }

    private fun getAllData() {
        viewModelScope.launch {
            repository.getAllExpenses().collectLatest { list ->
                var expense = 0
                var income = 0

                for (item in list) {
                    if (item.entryType == EntryType.Expense.name) {
                        expense += item.amount
                    } else {
                        income += item.amount
                    }
                }

                val balance = income - expense

                _state.update {
                    it.copy(
                        itemsList = list,
                        totalIncome = income.toString(),
                        totalExpense = expense.toString(),
                        totalBalance = balance.toString()
                    )
                }
            }
        }
    }


    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteExpense(id)
        }
    }
}