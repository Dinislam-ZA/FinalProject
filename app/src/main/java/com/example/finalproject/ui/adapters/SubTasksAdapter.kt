package com.example.finalproject.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.common.BaseAdapter
import com.example.finalproject.common.DiffUtilCallBackCommon
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.CategoryItemBinding
import com.example.finalproject.databinding.SubtaskListItemBinding
import com.example.finalproject.ui.SecondaryAdapterListener
import com.example.finalproject.ui.SubTaskItemsListener
import java.util.*


//class SubTasksAdapter (val context: Context,val listener: SubTaskItemsListener): BaseAdapter<SubTask, SubtaskListItemBinding>(context) {
//
//    override fun submitList(newItems: List<SubTask>) {
//        val diffCallback = SubTaskDiffUtil(items, newItems)
//        items = newItems
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        diffResult.dispatchUpdatesTo(this)
//    }
//
//    override fun createViewHolderAbstract(parent: ViewGroup, viewType: Int): ViewHolder {
//        binding = SubtaskListItemBinding.inflate(LayoutInflater.from(context), parent, false)
//
//        return ViewHolder(binding)
//    }
//
//    override fun getLayoutResId(position: Int): Int {
//        return R.layout.subtask_list_item
//    }
//
//    override fun onBind(binding: SubtaskListItemBinding, item: SubTask, position: Int) {
//        binding.subtaskTitle.setText(item.title)
//        binding.checkBox.isChecked = item.status
//
//
//    }
//}

//class SubTaskDiffUtil(private val oldItemList:List<SubTask>,
//                      private val newItemList:List<SubTask>):DiffUtilCallBackCommon<SubTask>(oldItemList, newItemList) {
//    override fun areItemsTheSameAbstract(oldItem: SubTask, newItem: SubTask): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentTheSameAbstract(oldItem: SubTask, newItem: SubTask): Boolean {
//        return oldItem == newItem
//    }
//}

class SubTaskDiffUtil(private val newList:List<SubTask>, private val oldList:List<SubTask>): DiffUtil.Callback(){
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newNote = newList[newItemPosition].id
        val oldNote = oldList[oldItemPosition].id

        return newNote == oldNote
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newTask = newList[newItemPosition]
        val oldTask = oldList[oldItemPosition]

        return newTask == oldTask
    }

}


class SubTasksAdapter(val context: Context, private val listener: SubTaskItemsListener): RecyclerView.Adapter<SubTasksAdapter.SubTaskViewHolder>() {

    private var items:List<SubTask> = listOf()

    fun submitList(newItems: List<SubTask>) {
        //val diffCallback = SubTaskDiffUtil(newItems, items)
        items = newItems
        //val diffResult = DiffUtil.calculateDiff(diffCallback)
        //diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    inner class SubTaskViewHolder(val binding: SubtaskListItemBinding, private val listener: SubTaskItemsListener) : RecyclerView.ViewHolder(binding.root) {
    }


    fun moveItem(fromPosition: Int, toPosition: Int) {
            // Перемещаем элементы в списке
            Collections.swap(items, fromPosition, toPosition)
            // Уведомляем адаптер об изменении позиции элементов
            notifyItemMoved(fromPosition, toPosition)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskViewHolder {
        val binding = SubtaskListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return SubTaskViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SubTaskViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                binding.subtaskTitle.setText(title)
                binding.checkbox.isChecked = status
                binding.subtaskTitle.setOnFocusChangeListener { view, b ->
                    if(!b){
                        listener.onSubTaskChanged(position)
                    }
                }

            }
        }
    }
}
