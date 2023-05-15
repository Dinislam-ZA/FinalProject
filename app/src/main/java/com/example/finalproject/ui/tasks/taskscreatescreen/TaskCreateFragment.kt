package com.example.finalproject.ui.tasks.taskscreatescreen

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.common.ExtraFunctions
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.databinding.CategoryBottomSheetBinding
import com.example.finalproject.databinding.FragmentTaskCreateBinding
import com.example.finalproject.ui.MenuAdapterListener
import com.example.finalproject.ui.SecondaryAdapterListener
import com.example.finalproject.ui.SubTaskItemsListener
import com.example.finalproject.ui.adapters.CategoriesBottomSheetAdapter
import com.example.finalproject.ui.adapters.NotesInTasksAdapter
import com.example.finalproject.ui.adapters.OnNoteInTaskClickListener
import com.example.finalproject.ui.adapters.SubTasksAdapter
import com.example.finalproject.ui.notes.notesmainscreen.NotesListAdapter
import com.example.finalproject.ui.tasks.tasksmainscreen.TasksMainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TaskCreateFragment : Fragment(), SecondaryAdapterListener, MenuAdapterListener, SubTaskItemsListener,
    OnNoteInTaskClickListener {

    companion object {
        fun newInstance() = TaskCreateFragment()
    }

   // private val viewModel: TaskCreateViewModel by viewModels {TaskCreateViewModel.Factory}

    private val viewModel: TasksMainViewModel by activityViewModels { TasksMainViewModel.Factory }
    private lateinit var binding: FragmentTaskCreateBinding

    private var isForCreate:Boolean = true

    private lateinit var categoriesAdapter: CategoriesBottomSheetAdapter
    private lateinit var subTasksAdapter: SubTasksAdapter
    private lateinit var notesAdapter: NotesInTasksAdapter

    private var categoriesList:List<Category> = listOf()
    private var categoryId:Long? = null
    private var categoryTV: TextView? = null
    private val selectedTask:Task? = null

    private var notesList:List<Note> = listOf(Note(title = "", id = null, createdAt = ""), Note(title = "Opa", id = null, createdAt = ""))
    private var subTasksList:MutableList<SubTask> = mutableListOf()

    private var dialog:BottomSheetDialog? = null
    private var toolBar: Toolbar? = null

    private var deadLine:Long? = null
    private var duration:Long? = null
    private var execDate:Long? = null
    private var execTime:Long? = null

    private val status: Int = 0

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskCreateBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.categories.collect{it -> categoriesChanges(it)}
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        categoryTV = binding.topicName
        toolBar = binding.toolbar

        isForCreate = isOpenForEdit()
        adaptersInitialize()

        binding.saveButton.setOnClickListener {
            viewModel.createOrUpdateTask(isForCreate
                ,binding.titleTV.text.toString()
                ,deadLine
                ,duration
                ,execDate
                ,execTime
                ,null
                , status
                , categoryId)
            view.findNavController().popBackStack()
        }

        binding.deadlineDateTv.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener {
                deadLine = it
                val dateString = dateFormat.format(deadLine)
                binding.deadlineDateTv.text = dateString
            }

            picker.show(childFragmentManager, picker.toString())
        }

        binding.executionDateTv.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener {
                execDate = it
                val dateString = dateFormat.format(execDate)
                binding.executionDateTv.text = dateString
            }

            picker.show(childFragmentManager, picker.toString())
        }

        binding.executionTimeTv.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .build()

            picker.addOnPositiveButtonClickListener {
                execTime = it.drawingTime
                var hourStr = "${picker.hour}"
                var minuteStr = "${picker.minute}"
                if(picker.hour < 10){
                    hourStr = "0" + "${picker.hour}"
                }
                if(picker.minute < 10){
                    minuteStr = "0" + "${picker.minute}"
                }
                binding.executionTimeTv.text = "$hourStr:$minuteStr"
            }

            picker.show(childFragmentManager, picker.toString())
        }

        binding.durationTv.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .build()

            picker.addOnPositiveButtonClickListener {
                duration = it.drawingTime
                var hourStr = "${picker.hour}"
                var minuteStr = "${picker.minute}"
                if(picker.hour < 10){
                    hourStr = "0" + "${picker.hour}"
                }
                if(picker.minute < 10){
                    minuteStr = "0" + "${picker.minute}"
                }
                binding.durationTv.text = "$hourStr:$minuteStr"
            }
            picker.show(childFragmentManager, picker.toString())
        }

        if(viewModel.selectedTask.value != null){
            isForCreate = false

        }

        binding.topicName.setOnClickListener {
            bottomSheetDialogAppear()
        }

        binding.backButton.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.addSubtask.setOnClickListener {
            val subTask = SubTask(title = "SubTask", status = false, task_id = -1, position = 2)
            position++
            subTasksList.add(subTask)
            subTasksAdapter.submitList(subTasksList)
        }

    }




    var position:Long = 1

    private fun categoriesChanges(categories: List<Category>){
        categoriesList = categories
        categoriesAdapter.categoriesList = categories
    }

    private fun notesChanges(notes: List<Note>){
        notesList = ExtraFunctions.concatenate(notesList, notes)
        notesAdapter.submitList(notes)
    }


    private fun bottomSheetDialogAppear(){
        val bottomSheetBinding = CategoryBottomSheetBinding.inflate(layoutInflater)
        val dialogView = bottomSheetBinding.root
        dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dialogView)
        with(bottomSheetBinding){
            chooseCategoryRV.adapter = categoriesAdapter
        }
        dialog?.show()
    }


    override fun onSecondaryListItemClick(position: Int) {
        categoryTV?.text = categoriesList[position].name
        toolBar?.backgroundTintList = ColorStateList.valueOf(categoriesList[position].color)
        categoryId = categoriesList[position].id
        dialog?.dismiss()
    }



    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDelete(position: Int, cardView: CardView) {
        TODO("Not yet implemented")
    }

    override fun onSubTaskChanged(position: Int) {
        viewModel.addSubTaskToSelectedTask(subTasksList[position].title, subTasksList[position].status, position)
    }

    private fun isOpenForEdit(): Boolean{
        val selectedTask = viewModel.selectedTask.value
        if(selectedTask != null){
            with(binding){
                titleTV.setText(selectedTask.title)
                if(selectedTask.deadLine != null) deadlineDateTv.text = dateFormat.format(selectedTask.deadLine)
                if(selectedTask.executionDate != null) executionDateTv.text = dateFormat.format(selectedTask.executionDate)
                if(selectedTask.executionTime != null) executionTimeTv.text = timeFormat.format(selectedTask.executionTime)
                if(selectedTask.taskDuration != null) durationTv.text = timeFormat.format(selectedTask.taskDuration)
            }


            lifecycleScope.launch {
                viewModel.subTasksForSelectedTask.collect{it -> subTasksAdapter.submitList(it)}
            }

//            lifecycleScope.launch {
//                viewModel.notesForSelectedTask.collect{it -> notesChanges(it)}
//            }
            return true
        }
        return false
    }

    private fun adaptersInitialize(){
        categoriesAdapter = context?.let { CategoriesBottomSheetAdapter(categoriesList, this, it) }!!
        notesAdapter = NotesInTasksAdapter( requireContext(), this)
        subTasksAdapter = SubTasksAdapter(requireContext(), this)
        val itemTouchCallback = ItemMoveCallback(subTasksAdapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.subtaskRv)
        binding.notesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.notesRv.adapter = notesAdapter
        binding.notesRv.isNestedScrollingEnabled = false
        binding.subtaskRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.subtaskRv.adapter = subTasksAdapter
        binding.subtaskRv.isNestedScrollingEnabled = false
        notesAdapter.submitList(notesList)
    }

    fun changeGraphToRoot(): NavController {
        val navController = binding.root.findNavController()
        navController.setGraph(R.navigation.main_fragment_navigation)
        return  navController
    }

    override fun onNoteClick(position: Int) {
      //  val navController = findNavController(R.id.mainFragment)
        binding.root.findNavController().setGraph(R.navigation.main_fragment_navigation)
        binding.root.findNavController().navigate(R.id.action_global_noteCreateFragment)
    }

    override fun onAddNoteClick() {
        binding.root.findNavController().setGraph(R.navigation.main_fragment_navigation)
        binding.root.findNavController().navigate(R.id.action_global_noteCreateFragment)
    }

}

class ItemMoveCallback(private val adapter: SubTasksAdapter) : ItemTouchHelper.Callback() {

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
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        adapter.moveItem(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}
