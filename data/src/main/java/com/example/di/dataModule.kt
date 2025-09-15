package com.example.di

import org.koin.dsl.module

import androidx.room.Room
import com.example.data.local.db.AppDatabase
import com.example.data.repository.ExpenseRepository
import com.example.data.repository.impl.ExpenseRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "expense_db"
        ).build()
    }
    single { get<AppDatabase>().expenseDao }
    singleOf(::ExpenseRepositoryImpl) { bind<ExpenseRepository>() }
}
