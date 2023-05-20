package com.example.finalproject.ui.tasks.tasksmainscreen

import android.app.Dialog
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.example.finalproject.R
import com.example.finalproject.common.ExtraFunctions
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.FragmentNotesMainBinding
import com.example.finalproject.databinding.FragmentTasksMainBinding
import com.example.finalproject.ui.MenuAdapterListener
import com.example.finalproject.ui.SecondaryAdapterListener
import com.example.finalproject.ui.adapters.CategoriesMainMenuAdapter
import com.example.finalproject.ui.adapters.SubTasksAdapter
import com.example.finalproject.ui.adapters.TaskAdapterListener
import com.example.finalproject.ui.adapters.TasksListAdapter
import com.example.finalproject.ui.notes.notesmainscreen.NotesListAdapter
import com.example.finalproject.ui.tasks.taskscreatescreen.ItemMoveCallback
import kotlinx.coroutines.launch

class TasksMainFragment : Fragment(), TaskAdapterListener, SecondaryAdapterListener {

    companion object {
        fun newInstance() = TasksMainFragment()
    }

    private val viewModel: TasksMainViewModel by activityViewModels { TasksMainViewModel.Factory }
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
        binding.tasksRcView.layoutManager = LinearLayoutManager(context)
        binding.tasksRcView.isNestedScrollingEnabled = false
        binding.tasksRcView.adapter = adapter

        val itemTouchCallback = ItemMoveCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.tasksRcView)

        categoriesMainMenuAdapter = context?.let {CategoriesMainMenuAdapter(categoriesList, this, it)}!!
        binding.categoriesRvMain.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.categoriesRvMain.isNestedScrollingEnabled = false
        binding.categoriesRvMain.adapter = categoriesMainMenuAdapter

        binding.taskSearchView.doOnTextChanged { text, start, before, count ->  searchTask(text.toString())}

        binding.addTaskButton.setOnClickListener {
            openTaskAddDialog()
        }
    }

    private fun openTaskAddDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.add_task_dialog)
        dialog.setTitle("Add task")
        dialog.setCancelable(true) // Разрешение закрытия диалога по клику вне него

        val editText = dialog.findViewById<EditText>(R.id.task_title_add)
        val button = dialog.findViewById<Button>(R.id.create_task_button)
        button.setOnClickListener {
            val text = editText.text.toString()
            viewModel.createTask(text)
            dialog.dismiss() // Закрытие диалогового окна
        }

        dialog.show() // Показ диалогового окна
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
        adapter.setTasksList(mTasksList)
    }

    private fun categoriesChanges(categories: List<Category>) {
        adapter.setCategoryList(categories)
        categoriesList = ExtraFunctions.concatenate(listOf(noCategoryItem), categories)
        categoriesMainMenuAdapter.categoriesList = categoriesList
        categoriesMainMenuAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        viewModel.selectTask(tasksList[position])
        binding.root.findNavController().navigate(R.id.action_tasksMainFragment_to_taskCreateFragment)
    }

    override fun onDeleteTask(position: Int) {
        viewModel.deleteTask(tasksList[position])
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

    private fun searchTask(text:String) {
        taskTextFilter = text
        tasksChanges(tasksList)
    }


}

class ItemMoveCallback(private val adapter: TasksListAdapter) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN  // Движение вверх и вниз разрешено
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT  // Свайп влево и вправо разрешен
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.deleteItem(viewHolder.adapterPosition)
    }

}