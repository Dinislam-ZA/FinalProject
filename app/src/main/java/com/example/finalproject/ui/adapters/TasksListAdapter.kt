package com.example.finalproject.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.TaskListItemBinding
import com.example.finalproject.ui.MenuAdapterListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class TasksListAdapter(private var tasksList: List<Task>, private val listener: TaskAdapterListener, val context: Context): RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {




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

    inner class TaskViewHolder(val binding: TaskListItemBinding, private val listener: TaskAdapterListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{


        init {
            binding.cardView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return TaskViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    fun formatTime(timeInMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60

        return "$hours hours $minutes minutes"
    }

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder){
            with(tasksList[position]){
                binding.title.text = title
                if(deadLine!=null) {
                    binding.deadline.text = dateFormat.format(deadLine)
                }
                if(taskDuration != null) {
                    binding.duration.text = formatTime(taskDuration!!)
                }
                binding.createdAt.text = createdAt
                binding.author.text = "by $author"
                val color = getCategoryColor(categorie)
                binding.cardView.backgroundTintList = ColorStateList.valueOf(color)
            }
        }
    }

    fun deleteItem(position: Int) {
        listener.onDeleteTask(position)
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

interface TaskAdapterListener {
    fun onItemClick(position:Int)

    fun onDeleteTask(position:Int)

}