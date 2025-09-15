package com.example.core

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

object PermissionHelper {
    fun requestPermission(
        activity: AppCompatActivity,
        permission: String,
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ): ActivityResultLauncher<String> {
        val launcher = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) onGranted() else onDenied()
        }
        launcher.launch(permission)
        return launcher
    }
}
