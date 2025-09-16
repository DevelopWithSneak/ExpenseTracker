package com.example.presentation.fragment.statistics

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.presentation.views.CustomMarkerView
import com.example.data.local.model.EntryType
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatisticsBinding {
        return FragmentStatisticsBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setupDropdown()
        setupChart()
    }

    override fun initObserver() {
        collectFlow(viewModel.state) { state ->
            val entries = state.itemsList.mapIndexed { index, item ->
                Entry(index.toFloat(), item.amount.toFloat())
            }
            updateChart(entries)
        }
    }
    private fun setupDropdown() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOf("Expense", "Income")
        )
        binding.typeDropdown.setAdapter(adapter)
        binding.typeDropdown.setText("Expense", false)
    }
    private fun setupChart() {
        binding.lineChart.apply {
            description.isEnabled = false
            axisRight.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
            }
            axisLeft.setDrawGridLines(false)

            marker = CustomMarkerView(requireContext(), R.layout.marker_view)
            legend.isEnabled = false
        }
    }
    override fun initListener() {}
    private fun updateChart(entries: List<Entry>) {
        if (entries.isEmpty()) {
            binding.lineChart.clear()
            return
        }

        val dataSet = LineDataSet(entries, "").apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawCircles(false)
            lineWidth = 2f
            color = ContextCompat.getColor(requireContext(), com.example.core.R.color.mainColor)
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(requireContext(), com.example.core.R.drawable.chart_gradient)
            setDrawValues(false)
        }

        binding.lineChart.data = LineData(dataSet)
        binding.lineChart.invalidate()
    }

    companion object {
        fun newInstance() = StatisticsFragment()
    }
}