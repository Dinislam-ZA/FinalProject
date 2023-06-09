package com.example.finalproject.ui.schedule.schedulemainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.FragmentScheduleMainBinding
import com.example.finalproject.ui.adapters.ScheduleListAdapter
import com.example.finalproject.ui.adapters.TaskAdapterListener
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ScheduleMainFragment : Fragment(), TaskAdapterListener {

    companion object {
        fun newInstance() = ScheduleMainFragment()
    }

    private val viewModel: ScheduleMainViewModel by viewModels { ScheduleMainViewModel.Factory }
    private lateinit var binding: FragmentScheduleMainBinding
    private var currentDate = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    private lateinit var tasksAdapter: ScheduleListAdapter

    private var tasks:List<Task> = listOf()
    private var categories:List<Category> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleMainBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.tasks.collect { tasks -> changeRCViewTasks(tasks)}
        }

        lifecycleScope.launch {
            viewModel.categories.collect { categories -> categoriesChanges(categories) }
        }

        return binding.root
    }

    private fun categoriesChanges(newCategories: List<Category>) {
        categories = newCategories
        tasksAdapter.setCategoryList(newCategories)
        tasksAdapter.notifyDataSetChanged()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateCurrentDateTextView()

        tasksAdapter = ScheduleListAdapter(this, requireContext())
        binding.taskList.layoutManager = LinearLayoutManager(requireContext())
        binding.taskList.adapter = tasksAdapter

        binding.nextDateButton.setOnClickListener {
            nextDayAction(it)
        }

        binding.previousDateButton.setOnClickListener {
            previousDayAction(it)
        }

        binding.monthDayText.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener {
                currentDate.timeInMillis = it
                updateCurrentDateTextView()
            }

            picker.show(childFragmentManager, picker.toString())
        }
    }

    fun newEventAction(view: View) {

    }

    private fun filterTasksByDate(selectedDate: Long): List<Task> {
        // Фильтрация задач по выбранной дате
        return tasks.filter {task -> task.executionDate != null  }.filter { task ->
            dateFormat.format(task.executionDate) == dateFormat.format(selectedDate)
        }
    }

    private fun changeRCViewTasks(newTasks: List<Task>){
        tasks = newTasks
        val filteredTaskList = filterTasksByDate(currentDate.timeInMillis)
        tasksAdapter.setTasksList(filteredTaskList.sortedBy { it.executionTime })
    }

    private fun nextDayAction(view: View) {
        currentDate.add(Calendar.DAY_OF_MONTH, 1)
        updateCurrentDateTextView()
        changeRCViewTasks(tasks)
    }

    private fun updateCurrentDateTextView() {
        binding.monthDayText.text = dateFormat.format(currentDate.timeInMillis)
        binding.dayOfWeekTV.text = dayOfWeek(currentDate.get(Calendar.DAY_OF_WEEK))
    }

    private fun previousDayAction(view: View) {
        currentDate.add(Calendar.DAY_OF_MONTH, -1)
        updateCurrentDateTextView()
        changeRCViewTasks(tasks)
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeleteTask(position: Int) {
        TODO("Not yet implemented")
    }

    private fun dayOfWeek(day:Int):String{
        when(day){
            1 -> return "Sunday"
            2 -> return "Monday"
            3 -> return "Tuesday"
            4 -> return "Wednesday"
            5 -> return "Thursday"
            6 -> return "Friday"
            7 -> return "Saturday"
        }
        return "No day"
    }

}