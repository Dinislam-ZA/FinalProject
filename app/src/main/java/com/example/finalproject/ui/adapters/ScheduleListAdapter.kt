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
import com.example.finalproject.databinding.ScheduleActionListItemBinding
import com.example.finalproject.databinding.TaskListItemBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


class ScheduleListAdapter(private val listener: TaskAdapterListener, val context: Context): RecyclerView.Adapter<ScheduleListAdapter.ScheduleTaskViewHolder>() {


    private var tasksList: List<Task> = listOf()
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

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

    private fun formatTime(timeInMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60

        return "$hours hours $minutes minutes"
    }

    inner class ScheduleTaskViewHolder(val binding: ScheduleActionListItemBinding, private val listener: TaskAdapterListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{


        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleTaskViewHolder {
        val binding = ScheduleActionListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return ScheduleTaskViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: ScheduleTaskViewHolder, position: Int) {
        with(holder){
            with(tasksList[position]){
                if (executionTime != null) {
                    binding.taskScheduleTime.text = timeFormat.format(executionTime)
                }
                binding.taskScheduleTitle.text = title
                var duration = "no duration"
                if (taskDuration != null) {
                    duration = formatTime(taskDuration!!)
                }
                binding.taskScheduleDuration.text = "duration - $duration"
                binding.taskDividerView.setBackgroundColor(getCategoryColor(categorie))
            }
        }
    }

    fun deleteItem(position: Int) {
        listener.onDeleteTask(position)
    }
}


