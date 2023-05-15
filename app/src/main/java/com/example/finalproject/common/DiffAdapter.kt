package com.example.finalproject.common

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(context: Context) : RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {

    var items = listOf<T>()
    protected lateinit var binding: VB

    abstract fun submitList(newItems: List<T>)

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutResId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return createViewHolderAbstract(parent, viewType)
    }

    abstract fun createViewHolderAbstract(parent: ViewGroup, viewType: Int): ViewHolder

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    protected abstract fun getLayoutResId(position: Int): Int

    inner class ViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, position: Int) {
            onBind(binding, item, position)

        }
    }

    protected abstract fun onBind(binding: VB, item: T,position: Int)
}


abstract class DiffUtilCallBackCommon<T>(private val oldItemList:List<T>,
                                      private val newItemList:List<T>): DiffUtil.Callback() {


    override fun getOldListSize() = oldItemList.size

    override fun getNewListSize() = newItemList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSameAbstract( oldItemList[oldItemPosition], newItemList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentTheSameAbstract( oldItemList[oldItemPosition], newItemList[newItemPosition])
    }

    abstract fun areItemsTheSameAbstract(oldItem:T, newItem:T): Boolean

    abstract fun areContentTheSameAbstract(oldItem:T, newItem:T): Boolean

}