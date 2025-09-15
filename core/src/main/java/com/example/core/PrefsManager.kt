package com.example.core

import android.content.Context
import androidx.core.content.edit

class PrefsManager(
    private val context: Context
) {
    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val prefEdit = prefs.edit()
}