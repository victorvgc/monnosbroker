package com.carvalho.monnosbroker.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.carvalho.monnosbroker.BR

open class BaseAdapter<T>(
    @LayoutRes val layoutId: Int,
    private val listener: ((T, position: Int) -> Unit)? = null
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    protected val itemList = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId, parent, false
        )
        return BaseViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        holder.bind(itemList[position], position)

    fun setupItems(items: List<T>) {
        this.itemList.clear()
        this.itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: T,  position: Int = -1) {
        if (position == -1) {
            this.itemList.add(item)
            notifyItemInserted(this.itemList.size)
        }  else {
            this.itemList.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun removeItemAt(position: Int) {
        this.itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItemAt(item: T, position: Int) {
        this.itemList[position] = item
        notifyItemChanged(position)
    }

    open class BaseViewHolder<T>(
        private val binding: ViewDataBinding,
        private val listener: ((T, position: Int) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        open fun bind(item: T, position: Int) {
            binding.setVariable(BR.item, item)
            itemView.setOnceClickListener { listener?.invoke(item, position) }
            binding.executePendingBindings()
        }
    }
}