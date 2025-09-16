package com.example.presentation.activities.splash

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.presentation.R
import com.example.presentation.activities.on_boarding.OnBoardingActivity
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var isMainLaunched = false
    override fun inflateBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(SystemBarStyle.light(getColor(com.example.core.R.color.mainColor), getColor(com.example.core.R.color.mainColor)))
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initView() {
        lifecycleScope.launch {
            delay(2000)
            launchMain()
        }
    }

    override fun initObserver() {}
    override fun initListener() {}


    private fun launchMain() {
        if (isMainLaunched) {
            return
        }
        isMainLaunched = true
        navigateTo(
            route = /*if (prefs.sessionCount > 0)*/ OnBoardingActivity::class.java, "isFromSplash" to true
        )
        finish()
    }

    override fun backPressed() {
        finish()
    }
}