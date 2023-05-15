package com.example.finalproject.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.TaskListItemBinding
import com.example.finalproject.ui.MenuAdapterListener

class TasksListAdapter(private var tasksList: List<Task>, private val listener: MenuAdapterListener, val context: Context): RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {




    private val colorCache = HashMap<Long?, Int>()
    private var categoryList: List<Category> = listOf()

    fun setCategoryList(categories:List<Category>){
        categoryList = categories
    }

    fun setTasksList(newTasksList: List<Task>){
        val diffCallback = TasksDiffCallback(newTasksList, tasksList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        tasksList = newTasksList
        diffResult.dispatchUpdatesTo(this)
    }

    private fun getCategoryById(id: Long?): Category? {
        if (id == null){
            return null
        }
        return categoryList.find { el -> el.id == id }
    }

    private fun getCategoryColor(categoryId: Long?): Int {
        if(categoryId != null){
            if (colorCache.containsKey(categoryId)) {
                // Возврат кэшированного цвета
                return colorCache[categoryId]!!
            } else {
                // Получение цвета из категории
                val category = getCategoryById(categoryId)
                val color = category?.color ?: return Color.parseColor("#59A5FF")
                colorCache[categoryId] = color
                return color
            }
        }
        return Color.parseColor("#59A5FF")
    }

    inner class TaskViewHolder(val binding: TaskListItemBinding, private val listener: MenuAdapterListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener{


        init {
            binding.cardView.setOnClickListener(this)
            binding.cardView.setOnLongClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(view: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onDelete(position, binding.cardView)
                return true
            }
            return false
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return TaskViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder){
            with(tasksList[position]){
                binding.title.text = title
                //binding.deadline.text = deadLine
                //binding.duration.text = taskDuration
                binding.createdAt.text = createdAt
                binding.author.text = "by $author"
                val color = getCategoryColor(categorie)
                binding.cardView.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
    }
}


class TasksDiffCallback(private val newList:List<Task>, private val oldList:List<Task>): DiffUtil.Callback(){
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