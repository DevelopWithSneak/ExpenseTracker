package com.example.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.adapter.ExpenseAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val adapter by lazy {
        ExpenseAdapter()
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initView() {

    }

    override fun initObserver() {
        collectFlow(viewModel.state) { state ->
            binding.balanceAmount.text = state.totalBalance
            binding.incomeBalance.text = state.totalIncome
            binding.expenseBalance.text = state.totalExpense
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(mContext)
            adapter.submitList(state.itemsList)
        }
    }

    override fun initListener() {
    }

    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}