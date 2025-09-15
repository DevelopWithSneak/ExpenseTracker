package com.example.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.data.model.DropDownItem
import com.example.presentation.databinding.DropdownItemBinding

class DropDownAdapter(
    context: Context,
    private val items: List<DropDownItem>
) : ArrayAdapter<DropDownItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: DropdownItemBinding

        val view: View
        if (convertView == null) {
            binding = DropdownItemBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as DropdownItemBinding
        }

        val item = getItem(position)
        item?.let {
            binding.itemIcon.setImageResource(it.icon)
            binding.itemText.text = it.name
        }

        return view
    }
}
