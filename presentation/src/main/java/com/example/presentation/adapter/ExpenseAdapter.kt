package com.example.presentation.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.example.core.R
import com.example.core.setTextColorRes
import com.example.data.local.model.EntryType
import com.example.data.local.model.Expense
import com.example.presentation.base.BaseListAdapter
import com.example.presentation.databinding.TransactionItemBinding

class ExpenseAdapter :
    BaseListAdapter<Expense>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean =
                oldItem == newItem
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return TransactionItemBinding.inflate(inflater, parent, false)
    }

    override fun bind(item: Expense, b: ViewBinding, position: Int) {
        val binding = b as TransactionItemBinding
        binding.title.text = item.title
        binding.amount.setTextColorRes(if (item.entryType == EntryType.Expense.name) R.color.redColor else R.color.mainColor)
        binding.icon.setImageResource(if (item.entryType == EntryType.Expense.name) R.drawable.ic_expense_item else R.drawable.ic_income_item)
        binding.amount.text = "${item.amount} $"
        binding.desc.text = DateFormat.format("dd MMM yyyy", item.date)
    }
}
