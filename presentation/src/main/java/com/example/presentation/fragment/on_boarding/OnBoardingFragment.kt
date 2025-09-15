package com.example.presentation.fragment.on_boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingBinding {
        return FragmentOnBoardingBinding.inflate(layoutInflater)
    }

    override fun initView() {
    }

    override fun initObserver() {
    }

    override fun initListener() {
    }

    companion object {
        fun newInstance(page: String) = OnBoardingFragment().apply {
            arguments = Bundle().apply {
                putString("name", page)
            }
        }
    }
}