package com.example.finalproject.ui.notes.notesmainscreen


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.databinding.FragmentNotesMainBinding
import com.example.finalproject.ui.MenuAdapterListener
import com.example.finalproject.ui.SecondaryAdapterListener
import com.example.finalproject.ui.adapters.CategoriesMainMenuAdapter
import kotlinx.coroutines.launch


class NotesMainFragment : Fragment(), MenuAdapterListener, SecondaryAdapterListener,
    PopupMenu.OnMenuItemClickListener {

    companion object {
        fun newInstance() = NotesMainFragment()
    }

    private val viewModel: NotesMainViewModel by viewModels { NotesMainViewModel.Factory }
    private lateinit var binding: FragmentNotesMainBinding
    private lateinit var adapter: NotesListAdapter
    private lateinit var categoriesMainMenuAdapter: CategoriesMainMenuAdapter

    private var notesList:List<Note> = listOf()
    private var selectedNote:Note? = null
    private val noCategoryItem = Category(null, "All", Color.parseColor("#59A5FF"))
    private var categoriesList:List<Category> = listOf(noCategoryItem)
    private var noteTextFilter:String = ""
    private var noteCategoryFilter:Long? = null

    private var popUpMenu:PopupMenu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesMainBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            viewModel.categories.collect { categories -> categoriesChanges(categories) }
        }

        lifecycleScope.launch {
            viewModel.notes.collect { notes -> notesChanges(notes)}
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = context?.let { NotesListAdapter(notesList, this, it) }!!
        binding.RCView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.RCView.adapter = adapter

        categoriesMainMenuAdapter = context?.let {CategoriesMainMenuAdapter(categoriesList, this, it)}!!
        binding.categoriesRvMain.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        binding.categoriesRvMain.adapter = categoriesMainMenuAdapter

        binding.addNoteButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_notesMainFragment_to_noteCreateFragment)
        }

        binding.noteSearchView.doOnTextChanged { text, start, before, count ->  searchNote(text.toString())}

    }

    private fun notesChanges(notes: List<Note>){
        notesList = notes
        var mNotesList = notesList
        if(noteCategoryFilter!= null){
            mNotesList = mNotesList.filter { el -> el.categorie == noteCategoryFilter }
        }
        if(noteTextFilter.isNotEmpty()){
            mNotesList = mNotesList.filter { el -> el.title.contains(noteTextFilter) || el.note.contains(noteTextFilter) }
        }
        adapter.notesList = mNotesList
        adapter.notifyDataSetChanged()
    }

    private fun categoriesChanges(categories: List<Category>){
        adapter.setCategoryList(categories)
        adapter.notifyDataSetChanged()
        categoriesList = concatenate(listOf(noCategoryItem), categories)
        categoriesMainMenuAdapter.categoriesList = categoriesList
        categoriesMainMenuAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position:Int){
        val bundle = bundleOf("id" to notesList[position].id, "title" to notesList[position].title)
        binding.root.findNavController().navigate(R.id.action_notesMainFragment_to_noteCreateFragment, bundle)
    }

    override fun onDelete(position: Int, cardView: CardView) {

        popUpAppear(cardView)

        val note = Note(notesList[position].id,
            notesList[position].title,
            notesList[position].note,
            notesList[position].createdAt)

        selectedNote = note
    }

    private fun popUpAppear(cardView: CardView) {

        popUpMenu = context?.let { PopupMenu(it, cardView) }
        popUpMenu?.setOnMenuItemClickListener(this)
        popUpMenu?.inflate(R.menu.pop_up_menu)
        popUpMenu?.show()

    }

    private fun searchNote(text:String) {
        noteTextFilter = text
        notesChanges(notesList)
    }


    private fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }

    override fun onSecondaryListItemClick(position: Int) {
        if (position == 0){
            binding.infoList.text = resources.getString(R.string.notes_default_info)
            noteCategoryFilter = null
        }
        else{
            binding.infoList.text = categoriesList[position].name
            noteCategoryFilter = categoriesList[position].id
        }
        notesChanges(notesList)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_item){
            viewModel.deleteNote(selectedNote!!)
            return true
        }
        return false
    }

}

