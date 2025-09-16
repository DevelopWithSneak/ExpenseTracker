package com.example.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : Any>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseListAdapter.BaseViewHolder<out ViewBinding>>(diffCallback) {

    var onItemClick: ((position: Int, item: T) -> Unit)? = null
    var onItemLongClick: ((position: Int, item: T) -> Boolean)? = null

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewBinding

    abstract fun bind(item: T, b: ViewBinding, position: Int)

    open fun getItemViewTypeFor(position: Int, item: T): Int = 0

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypeFor(position, getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out ViewBinding> {
        val binding = getViewBinding(LayoutInflater.from(parent.context), parent, viewType)
        val holder = BaseViewHolder(binding)

        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick?.invoke(position, getItem(position))
            }
        }

        holder.itemView.setOnLongClickListener {
            val position = holder.bindingAdapterPosition
            return@setOnLongClickListener if (position != RecyclerView.NO_POSITION) {
                onItemLongClick?.invoke(position, getItem(position)) ?: false
            } else false
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out ViewBinding>, position: Int) {
        bind(getItem(position), holder.binding, position)
    }

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)
}
