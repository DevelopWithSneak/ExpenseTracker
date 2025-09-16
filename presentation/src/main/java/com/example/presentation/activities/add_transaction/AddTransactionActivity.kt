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
    private var expenseId: Long = -1L
    override fun inflateBinding(): ActivityAddTransactionBinding {
        return ActivityAddTransactionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            SystemBarStyle.light(
                getColor(com.example.core.R.color.mainColor),
                getColor(com.example.core.R.color.mainColor)
            )
        )
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun initView() {
        expenseId = intent.getLongExtra("expense_id", -1L)
        if (expenseId != -1L) {
            lifecycleScope.launch(Dispatchers.IO) {
                val expense = expenseRepository.getExpenseById(expenseId)
                expense?.let {
                    withContext(Dispatchers.Main) {
                        binding.topBarTitle.text = getString(R.string.update_transaction)
                        binding.titleEd.setText(it.title)
                        binding.amountEd.setText(it.amount.toString())
                        binding.entrySwitch.isChecked = it.entryType == EntryType.Expense.name
                        binding.entryText.text =
                            if (it.entryType == EntryType.Expense.name) getString(R.string.expense)
                            else getString(R.string.income)
                        binding.addBtn.text = getString(R.string.update_transaction)
                    }
                }
            }
        } else {
            binding.entryText.text = getString(R.string.income)
            binding.entrySwitch.isChecked = false
        }
    }

    override fun initObserver() {
    }

    override fun initListener() {
        binding.addBtn.setSafeOnClickListener {
            val title = binding.titleEd.text.toString().trim()
            val amountText = binding.amountEd.text.toString().trim()
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
                id = if (expenseId == -1L) 0 else expenseId,
                title = title,
                amount = amountText.toInt(),
                entryType = entryType,
                date = System.currentTimeMillis()
            )

            lifecycleScope.launch(Dispatchers.IO) {
                expenseRepository.upsertExpense(expense)
                withContext(Dispatchers.Main) {
                    showToast(
                        if (expenseId == -1L) "Transaction added successfully"
                        else "Transaction updated successfully"
                    )
                    backPressed()
                }
            }
        }
        binding.backBtn.setSafeOnClickListener {
            backPressed()
        }
    }

    override fun backPressed() {
        finish()
    }
}