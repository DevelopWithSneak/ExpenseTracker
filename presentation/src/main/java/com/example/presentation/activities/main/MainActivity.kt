package com.example.presentation.activities.main

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.core.setSafeOnClickListener
import com.example.presentation.R
import com.example.presentation.activities.add_transaction.AddTransactionActivity
import com.example.presentation.base.BaseActivity
import com.example.presentation.base.BaseViewPagerAdapter
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.fragment.home.HomeFragment
import com.example.presentation.fragment.statistics.StatisticsFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val homeFragment by lazy {
        HomeFragment()
    }
    private val pagerAdapter: BaseViewPagerAdapter by lazy {
        BaseViewPagerAdapter(
            this, listOf(
                homeFragment,
                StatisticsFragment(),
            )
        )
    }

    override fun inflateBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
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
        binding.pager.apply {
            adapter = pagerAdapter
            isUserInputEnabled = true
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    changePosition(position)
                }
            })
        }
    }

    override fun initObserver() {}
    override fun initListener() {
        binding.homeLayout.setSafeOnClickListener {
            binding.pager.setCurrentItem(0, false)
        }
        binding.statisticsLayout.setSafeOnClickListener {
            binding.pager.setCurrentItem(1, false)
        }
        binding.addBtn.setSafeOnClickListener {
            navigateTo(AddTransactionActivity::class.java, "expense_id" to -1)
        }
        homeFragment.onItemClick={_,item->
            navigateTo(AddTransactionActivity::class.java, "expense_id" to item.id)
        }
    }

    private fun changePosition(selectedPosition: Int = 0) {
        when (selectedPosition) {
            0 -> {
                binding.apply {
                    homeImage.setImageResource(com.example.core.R.drawable.ic_home_selected)
                    statisticsImage.setImageResource(com.example.core.R.drawable.ic_statistics_unselected)
                }
            }

            1 -> {
                binding.apply {
                    homeImage.setImageResource(com.example.core.R.drawable.ic_home_unselected)
                    statisticsImage.setImageResource(com.example.core.R.drawable.ic_statistics_selected)
                }
            }
        }
    }

    override fun backPressed() {
        finish()
    }
}