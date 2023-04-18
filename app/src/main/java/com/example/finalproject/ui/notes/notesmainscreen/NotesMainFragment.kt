package com.example.finalproject.ui.notes.notesmainscreen

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.databinding.FragmentNotesMainBinding
import com.example.finalproject.ui.MyClickListener
import com.example.finalproject.ui.adapters.CategoriesMainMenuAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesMainFragment : Fragment(), MyClickListener {

    companion object {
        fun newInstance() = NotesMainFragment()
    }

    private val viewModel: NotesMainViewModel by viewModels { NotesMainViewModel.Factory }
    private lateinit var binding: FragmentNotesMainBinding
    private lateinit var adapter: NotesListAdapter
    private lateinit var categoriesMainMenuAdapter: CategoriesMainMenuAdapter

    private var notesList:List<Note> = listOf()
    private var categoriesList:List<Category> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesMainBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = context?.let { NotesListAdapter(notesList, this, it) }!!
        binding.RCView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.RCView.adapter = adapter

        categoriesMainMenuAdapter = context?.let {CategoriesMainMenuAdapter(categoriesList, this, it)}!!
        binding.categoriesRvMain.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.categoriesRvMain.adapter = categoriesMainMenuAdapter

        binding.addNoteButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_notesMainFragment_to_noteCreateFragment)
        }



        lifecycleScope.launch {
            viewModel.notes.collect { notes -> notesChanges(notes)}
        }

        lifecycleScope.launch {
            viewModel.categories.collect { categories -> categoriesChanges(categories) }
        }


        return view
    }

    private fun notesChanges(notes: List<Note>){
        notesList = notes
        adapter.notesList = notes
        adapter.notifyDataSetChanged()
    }

    private fun categoriesChanges(categories: List<Category>){
        adapter.setCategoryList(categories)
        categoriesMainMenuAdapter.categoriesList = categories
        categoriesList = categories
        categoriesMainMenuAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position:Int){
        val bundle = bundleOf("id" to notesList[position].id, "title" to notesList[position].title)
        binding.root.findNavController().navigate(R.id.action_notesMainFragment_to_noteCreateFragment, bundle)
    }

    override fun onDeleteClick(position: Int) {
        val note = Note(notesList[position].id,
            notesList[position].title,
            notesList[position].note,
            notesList[position].createdAt)

        viewModel.deleteNote(note)
    }

    override fun onCategoryClick(position: Int) {

    }


}

