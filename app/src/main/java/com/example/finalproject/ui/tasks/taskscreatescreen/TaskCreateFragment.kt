package com.example.finalproject.ui.tasks.taskscreatescreen

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
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

class TaskCreateFragment : Fragment(), SecondaryAdapterListener, SubTaskItemsListener,
    OnNoteInTaskClickListener, PopupMenu.OnMenuItemClickListener {

    companion object {
        fun newInstance() = TaskCreateFragment()
    }


    private var popUpMenu:PopupMenu? = null

    private val viewModel: TasksMainViewModel by activityViewModels { TasksMainViewModel.Factory }
    private lateinit var binding: FragmentTaskCreateBinding

    private var isForCreate:Boolean = true

    private lateinit var categoriesAdapter: CategoriesBottomSheetAdapter
    private lateinit var subTasksAdapter: SubTasksAdapter
    private lateinit var notesAdapter: NotesInTasksAdapter

    private var categoriesList:List<Category> = listOf()
    private var categoryId:Long? = null
    private var color:Int? = null
    private var categoryTV: TextView? = null
    private var selectedTask:Task? = null

    private var notesList:List<Note> = listOf()
    private var subTasksList:List<SubTask> = listOf()

    private var dialog:BottomSheetDialog? = null
    private var toolBar: Toolbar? = null

    private var deadLine:Long? = null
    private var duration:Long? = null
    private var execDate:Long? = null
    private var execTime:Long? = null


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
            val status = sumSubTaskProgress()
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
                binding.deadlineDateTv.text = "$dateString - deadline"
            }

            picker.show(childFragmentManager, picker.toString())
        }

        binding.executionDateTv.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener {
                execDate = it
                val dateString = dateFormat.format(execDate)
                binding.executionDateTv.text = "$dateString - execution date"
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
                execTime = (picker.hour*3600*1000 + picker.minute*60*1000).toLong()
                var hourStr = "${picker.hour}"
                var minuteStr = "${picker.minute}"
                if(picker.hour < 10){
                    hourStr = "0" + "${picker.hour}"
                }
                if(picker.minute < 10){
                    minuteStr = "0" + "${picker.minute}"
                }
                binding.executionTimeTv.text = "$hourStr:$minuteStr - execution time"
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
                duration = (picker.hour*3600*1000 + picker.minute*60*1000).toLong()
                var hourStr = "${picker.hour}"
                var minuteStr = "${picker.minute}"
                if(picker.hour < 10){
                    hourStr = "0" + "${picker.hour}"
                }
                if(picker.minute < 10){
                    minuteStr = "0" + "${picker.minute}"
                }
                binding.durationTv.text = "$hourStr:$minuteStr - duration"
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
            val subTask = SubTask(title = "SubTask$position", status = false, task_id = selectedTask?.id, position = position)
            viewModel.addSubTaskToSelectedTask(subTask)
        }

    }

    var position:Int = 1



    private fun categoriesChanges(categories: List<Category>){
        categoriesList = categories
        categoriesAdapter.categoriesList = categories
    }

    private fun notesChanges(notes: List<Note>){
        notesList = ExtraFunctions.concatenate(listOf(Note(title = "", id = -1, createdAt = "")), notes)
        notesAdapter.submitList(notesList)
    }

    private fun subTasksChanges(subtasks: List<SubTask>){
        subTasksList = subtasks
        position = subTasksList.size + 1
        subTasksAdapter.submitList(subTasksList)
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
        binding.topicName.setTextColor(categoriesList[position].color)
        categoryId = categoriesList[position].id
        dialog?.dismiss()
    }

    override fun onSubTaskTextChanged(text: String, position: Int) {
        val subTask = subTasksList[position]
        subTask.title = text
        viewModel.addSubTaskToSelectedTask(subTask)
    }

    private fun sumSubTaskProgress():Int{
        var progress:Int = 0
        val subTaskNumber = subTasksList.size
        subTasksList.map { subTask -> if(subTask.status) progress++ }
        return if (progress!=0)
            subTaskNumber/progress
        else
            0
    }

    override fun onSubTaskStatusChanged(isChecked: Boolean, position: Int) {
        val subTask = subTasksList[position]
        subTask.status = isChecked
        viewModel.addSubTaskToSelectedTask(subTask)
    }

    override fun onSubTaskMoved(fromPos: Int, toPos: Int) {
        val subTaskFirst = subTasksList[fromPos]
        val subTaskSecond = subTasksList[toPos]
        val pos = subTaskFirst.position
        subTaskFirst.position = subTaskSecond.position
        subTaskSecond.position = pos
        viewModel.addSubTaskToSelectedTask(subTaskSecond)
        viewModel.addSubTaskToSelectedTask(subTaskFirst)
    }

    override fun deleteSubTask(position: Int) {
        viewModel.deleteSubTask(subTasksList[position])
    }

    private fun isOpenForEdit(): Boolean{
        val mSelectedTask = viewModel.selectedTask.value
        selectedTask = mSelectedTask
        if(mSelectedTask != null){
            with(binding){
                titleTV.setText(mSelectedTask.title)
                if(mSelectedTask.deadLine != null) {
                    deadlineDateTv.text = dateFormat.format(mSelectedTask.deadLine)
                    deadLine = mSelectedTask.deadLine
                }
                if(mSelectedTask.executionDate != null) {
                    executionDateTv.text = dateFormat.format(mSelectedTask.executionDate)
                    execDate = mSelectedTask.executionDate
                }
                if(mSelectedTask.executionTime != null) {
                    executionTimeTv.text = timeFormat.format(mSelectedTask.executionTime)
                    execTime = mSelectedTask.executionTime
                }
                if(mSelectedTask.taskDuration != null) {
                    durationTv.text = timeFormat.format(mSelectedTask.taskDuration)
                    duration = mSelectedTask.taskDuration
                }
                categoryId = mSelectedTask.categorie
            }

            viewModel.selectedTaskCategory.observe(viewLifecycleOwner){
                if (it!=null){
                    color = it.color
                    notesAdapter.setColor(color)
                    categoryTV!!.text = it.name
                    binding.topicName.setTextColor(color!!)
                }
            }

            lifecycleScope.launch {
                viewModel.subTasksForSelectedTask.collect{it -> subTasksChanges(it)}
            }

            lifecycleScope.launch {
                viewModel.notesForSelectedTask.collect{it -> notesChanges(it)}
            }
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

    override fun onNoteClick(position: Int) {
        val bundle = bundleOf("id" to notesList[position].id, "title" to notesList[position].title)
        binding.root.findNavController().navigate(R.id.action_taskCreateFragment_to_noteCreateFragment, bundle)
    }

    private var selectedNote:Note? = null
    override fun onNoteLongClick(position: Int, cardView: CardView) {
        popUpAppear(cardView)

        selectedNote = notesList[position]
    }

    override fun onAddNoteClick() {
        val bundle = bundleOf("task_id" to selectedTask?.id)
        binding.root.findNavController().navigate(R.id.action_taskCreateFragment_to_noteCreateFragment, bundle)
    }

    private fun popUpAppear(cardView: CardView) {
        popUpMenu = context?.let { PopupMenu(it, cardView) }
        popUpMenu?.setOnMenuItemClickListener(this)
        popUpMenu?.inflate(R.menu.pop_up_menu)
        popUpMenu?.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_item){
            selectedNote?.id?.let { viewModel.removeNoteFromSelectedTask(it) }
            return true
        }
        return false
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
        adapter.deleteItem(viewHolder.adapterPosition)
    }

}
