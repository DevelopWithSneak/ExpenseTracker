package com.example.presentation.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.presentation.BuildConfig
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.Serializable

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
        private set
    protected lateinit var mContext: Activity
    private var lastClickTime = 0L
    abstract fun inflateBinding(): VB
    abstract fun backPressed()
    abstract fun initView()
    abstract fun initObserver()
    abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        binding = inflateBinding()
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPressed()
                }
            })
        initView()
        initObserver()
        initListener()
    }
    fun <T> AppCompatActivity.collectChannel(
        channel: ReceiveChannel<T>,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        collector: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state) {
                for (item in channel) {
                    collector(item)
                }
            }
        }
    }
    fun <T> AppCompatActivity.collectFlow(
        flow: Flow<T>,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        collector: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state) {
                flow.collect(collector)
            }
        }
    }

    protected fun navigateTo(
        route: Class<out AppCompatActivity>,
        vararg extras: Pair<String, Any?>,
        debounceTime: Long = 800L
    ) {
        if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
        lastClickTime = SystemClock.elapsedRealtime()

        val mIntent = Intent(mContext, route)

        if (extras.isNotEmpty()) {
            extras.forEach { (key, value) ->
                when (value) {
                    is String -> mIntent.putExtra(key, value)
                    is Int -> mIntent.putExtra(key, value)
                    is Boolean -> mIntent.putExtra(key, value)
                    is Float -> mIntent.putExtra(key, value)
                    is Double -> mIntent.putExtra(key, value)
                    is Long -> mIntent.putExtra(key, value)
                    is Parcelable -> mIntent.putExtra(key, value)
                    is Serializable -> mIntent.putExtra(key, value)
                    null -> mIntent.putExtra(key, null as Serializable?)
                }
            }
        }

        startActivity(mIntent)
    }
}