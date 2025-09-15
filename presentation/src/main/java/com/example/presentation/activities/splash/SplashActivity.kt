package com.example.presentation.activities.splash

import android.animation.ValueAnimator
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
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private var valueAnimator: ValueAnimator? = null
    private var isValueAnimatorDone = false
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
            startAnimator()
            launchMain()
        }
    }

    override fun initObserver() {}
    override fun initListener() {}
    private fun startAnimator(duration: Long = 5) {
        binding.apply {
            valueAnimator = ValueAnimator.ofFloat(0f, 100f)
            valueAnimator?.let {
                it.duration = duration * 1000
                it.addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    progress.progress = value.toInt()
                }
                it.start()
            }
        }
    }

    private fun completeAnimator() {
        pauseValueAnimator()
        isValueAnimatorDone = true
        binding.progress.progress = 100
    }

    private fun pauseValueAnimator() {
        valueAnimator?.apply {
            if (this.isRunning) {
                this.pause()
            }
        }

    }

    private fun launchMain() {
        if (isMainLaunched) {
            return
        }
        isMainLaunched = true
        completeAnimator()
        navigateTo(
            route = /*if (prefs.sessionCount > 0)*/ OnBoardingActivity::class.java, "isFromSplash" to true
        )
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (!isValueAnimatorDone) {
            valueAnimator?.apply {
                if (this.isPaused) {
                    this.resume()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pauseValueAnimator()
    }

    override fun backPressed() {
        finish()
    }
}