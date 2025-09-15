package com.example.presentation.activities.on_boarding

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.setSafeOnClickListener
import com.example.presentation.R
import com.example.presentation.activities.main.MainActivity
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {

    override fun inflateBinding(): ActivityOnBoardingBinding {
        return ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            SystemBarStyle.light(
                getColor(com.example.core.R.color.onboardingColor), getColor(
                    com.example.core.R.color.onboardingColor
                )
            )
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initView() {

    }

    override fun initObserver() {
    }

    override fun initListener() {
        binding.apply {
            nextBtn.setSafeOnClickListener {
                navigateTo(route = MainActivity::class.java)
                finish()
            }
        }
    }

    override fun backPressed() {
        finish()
    }
}