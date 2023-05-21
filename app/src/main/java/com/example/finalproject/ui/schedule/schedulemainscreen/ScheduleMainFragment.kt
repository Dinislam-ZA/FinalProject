package com.example.finalproject.ui.schedule.schedulemainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.FragmentCategoriesBinding
import com.example.finalproject.databinding.FragmentScheduleMainBinding
import com.example.finalproject.ui.adapters.NotesInTasksAdapter
import com.example.finalproject.ui.adapters.ScheduleListAdapter
import com.example.finalproject.ui.adapters.TaskAdapterListener
import com.example.finalproject.ui.profile.categoriesscreen.CategoriesViewModel
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
        binding.monthDayText.text = dateFormat.format(currentDate.timeInMillis)

        tasksAdapter = ScheduleListAdapter(this, requireContext())
        binding.taskList.layoutManager = LinearLayoutManager(requireContext())
        binding.taskList.adapter = tasksAdapter

        binding.nextDateButton.setOnClickListener {
            nextDayAction(it)
        }

        binding.previousDateButton.setOnClickListener {
            previousDayAction(it)
        }
    }

    fun newEventAction(view: View) {

    }

    private fun filterTasksByDate(selectedDate: Long): List<Task> {
        // Фильтрация задач по выбранной дате
        return tasks.filter { task ->
            // Вам нужно заменить это условие на фактическую проверку даты в вашей модели Task
            // Например, если у вас есть поле "dueDate" в модели Task, то условие может быть таким:
            dateFormat.format(task.executionDate) == dateFormat.format(selectedDate)
        }
    }

    private fun changeRCViewTasks(newTasks: List<Task>){
        tasks = newTasks
        val filteredTaskList = filterTasksByDate(currentDate.timeInMillis)
        tasksAdapter.setTasksList(filteredTaskList)
    }

    private fun nextDayAction(view: View) {
        currentDate.add(Calendar.DAY_OF_MONTH, 1)
        updateCurrentDateTextView()
        changeRCViewTasks(tasks)
    }

    private fun updateCurrentDateTextView() {
        binding.monthDayText.text = dateFormat.format(currentDate.timeInMillis)
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


}