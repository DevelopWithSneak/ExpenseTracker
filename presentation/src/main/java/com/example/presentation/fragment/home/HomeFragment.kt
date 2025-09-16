package com.example.presentation.fragment.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.R
import com.example.data.local.model.Expense
import com.example.presentation.adapter.ExpenseAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val adapter by lazy {
        ExpenseAdapter()
    }
    private var isVisible = true
    var onItemClick: ((Int, Expense) -> Unit)? = null
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    override fun initObserver() {
        collectFlow(viewModel.state) { state ->
            if (isVisible) {
                binding.balanceAmount.text = state.totalBalance
                binding.incomeBalance.text = state.totalIncome
                binding.expenseBalance.text = state.totalExpense
            } else {
                binding.balanceAmount.text = "****"
                binding.incomeBalance.text = "****"
                binding.expenseBalance.text = "****"
            }
            adapter.submitList(state.itemsList)
        }
    }

    override fun initListener() {
        binding.visibilityBtn.setOnClickListener {
            isVisible = !isVisible
            updateVisibilityUI()
        }
        adapter.onItemClick = { index, item ->
            onItemClick?.invoke(index,item)
        }
        adapter.onItemLongClick = { _, item ->
            showDeleteDialog(item.id)
            true
        }
    }

    private fun updateVisibilityUI() {
        if (isVisible) {
            binding.visibilityBtn.setImageResource(R.drawable.ic_visibility)
            binding.balanceAmount.text = viewModel.state.value.totalBalance
            binding.incomeBalance.text = viewModel.state.value.totalIncome
            binding.expenseBalance.text = viewModel.state.value.totalExpense
        } else {
            binding.visibilityBtn.setImageResource(R.drawable.ic_visibility_off)
            binding.balanceAmount.text = "****"
            binding.incomeBalance.text = "****"
            binding.expenseBalance.text = "****"
        }
    }

    private fun showDeleteDialog(id: Long) {
        AlertDialog.Builder(mContext)
            .setTitle("Delete Transaction")
            .setMessage("Are you sure you want to delete this transaction?")
            .setPositiveButton("Delete") { dialog, _ ->
                viewModel.deleteNote(id)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}