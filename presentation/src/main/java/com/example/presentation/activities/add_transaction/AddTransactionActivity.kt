package com.example.presentation.activities.add_transaction

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.core.setSafeOnClickListener
import com.example.core.showToast
import com.example.data.local.model.EntryType
import com.example.data.local.model.Expense
import com.example.presentation.R
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        binding.entryText.text = getString(R.string.income)
        binding.entrySwitch.isChecked = false
    }
    override fun initObserver() {
    }
    override fun initListener() {
        binding.entrySwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.entryText.text = if (isChecked) {
                getString(R.string.expense)
            } else {
                getString(R.string.income)
            }
        }
        binding.backBtn.setSafeOnClickListener {
            backPressed()
        }
        binding.addBtn.setSafeOnClickListener {
            val title = binding.titleEd.text.toString().trim()
            val amountText = binding.amountEd.text.toString().trim()

            // validation
            if (title.isEmpty() || amountText.isEmpty()) {
                showToast("Please fill all fields")
                return@setSafeOnClickListener
            }

            val entryType = if (binding.entrySwitch.isChecked) {
                EntryType.Expense.name
            } else {
                EntryType.Income.name
            }

            val expense = Expense(
                title = title,
                amount = amountText.toInt(),
                entryType = entryType,
                date = System.currentTimeMillis()
            )

            lifecycleScope.launch(Dispatchers.IO) {
                expenseRepository.upsertExpense(expense)
                withContext(Dispatchers.Main) {
                    showToast("Transaction added successfully")
                    backPressed()
                }
            }
        }
    }
    override fun backPressed() {
        finish()
    }
}