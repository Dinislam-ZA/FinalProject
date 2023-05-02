package com.example.finalproject.ui.tasks.tasksmainscreen

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.common.ExtraFunctions
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.FragmentNotesMainBinding
import com.example.finalproject.databinding.FragmentTasksMainBinding
import com.example.finalproject.ui.MenuAdapterListener
import com.example.finalproject.ui.SecondaryAdapterListener
import com.example.finalproject.ui.adapters.CategoriesMainMenuAdapter
import com.example.finalproject.ui.adapters.TasksListAdapter
import com.example.finalproject.ui.notes.notesmainscreen.NotesListAdapter
import kotlinx.coroutines.launch

class TasksMainFragment : Fragment(), MenuAdapterListener, SecondaryAdapterListener {

    companion object {
        fun newInstance() = TasksMainFragment()
    }

    private val viewModel: TasksMainViewModel by viewModels { TasksMainViewModel.Factory }
    private lateinit var binding: FragmentTasksMainBinding
    private lateinit var adapter: TasksListAdapter
    private lateinit var categoriesMainMenuAdapter: CategoriesMainMenuAdapter

    private var tasksList:List<Task> = listOf()
    private var selectedtask: Task? = null
    private val noCategoryItem = Category(null, "All", Color.parseColor("#59A5FF"))
    private var categoriesList:List<Category> = listOf(noCategoryItem)
    private var taskTextFilter:String = ""
    private var taskCategoryFilter:Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksMainBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.categories.collect { categories -> categoriesChanges(categories) }
        }

        lifecycleScope.launch {
            viewModel.tasks.collect { tasks -> tasksChanges(tasks)}
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = context?.let { TasksListAdapter(tasksList, this, it) }!!
        binding.RCView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.RCView.isNestedScrollingEnabled = false
        binding.RCView.adapter = adapter

        categoriesMainMenuAdapter = context?.let {CategoriesMainMenuAdapter(categoriesList, this, it)}!!
        binding.categoriesRvMain.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.categoriesRvMain.isNestedScrollingEnabled = false
        binding.categoriesRvMain.adapter = categoriesMainMenuAdapter
    }

    private fun tasksChanges(tasks: List<Task>) {
        tasksList = tasks
        var mTasksList = tasksList
        if(taskCategoryFilter!= null){
            mTasksList = mTasksList.filter { el -> el.categorie == taskCategoryFilter }
        }
        if(taskTextFilter.isNotEmpty()){
            mTasksList = mTasksList.filter { el -> el.title.lowercase().contains(taskTextFilter.lowercase()) }
        }
        adapter.setNotesList(mTasksList)
    }

    private fun categoriesChanges(categories: List<Category>) {
        adapter.setCategoryList(categories)
        adapter.notifyDataSetChanged()
        categoriesList = ExtraFunctions.concatenate(listOf(noCategoryItem), categories)
        categoriesMainMenuAdapter.categoriesList = categoriesList
        categoriesMainMenuAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDelete(position: Int, cardView: CardView) {
        TODO("Not yet implemented")
    }

    override fun onSecondaryListItemClick(position: Int) {
        if (position == 0){
            binding.infoList.text = resources.getString(R.string.notes_default_info)
            taskCategoryFilter = null
        }
        else{
            binding.infoList.text = categoriesList[position].name
            taskCategoryFilter = categoriesList[position].id
        }
        tasksChanges(tasksList)
    }


}