package com.example.expensetracker

import android.app.Application
import com.example.di.appModule
import com.example.di.coreModule
import com.example.di.dataModule
import com.example.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ExpenseTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ExpenseTrackerApp)
            modules(appModule)
            modules(coreModule)
            modules(dataModule)
            modules(presentationModule)
        }
    }
}
