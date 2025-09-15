package com.example.data.local.model

data class Expense(
    val id: Long = 0,
    val title: String,
    val amount: Int,
    val entryType: String,
    val date: Long
)