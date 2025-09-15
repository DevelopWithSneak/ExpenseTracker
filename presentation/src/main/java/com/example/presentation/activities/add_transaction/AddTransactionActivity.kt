package com.example.presentation.activities.add_transaction

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.setSafeOnClickListener
import com.example.data.model.DropDownItem
import com.example.presentation.R
import com.example.presentation.adapter.DropDownAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : BaseActivity<ActivityAddTransactionBinding>() {
    override fun inflateBinding(): ActivityAddTransactionBinding {
        return ActivityAddTransactionBinding.inflate(layoutInflater)
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
        val items = listOf(
            DropDownItem(com.example.core.R.drawable.ic_upwork, "Upwork"),
        )
        val adapter = DropDownAdapter(this, items)
        binding.nameDropDown.setAdapter(adapter)
        binding.nameDropDown.setText(items.first().name, false)
    }
    override fun initObserver() {}
    override fun initListener() {
        binding.backBtn.setSafeOnClickListener {
            backPressed()
        }
    }
    override fun backPressed() {
        finish()
    }
}