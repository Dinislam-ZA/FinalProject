package com.example.finalproject.ui.notes.notecreatescreen

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.data.model.Category
import com.example.finalproject.databinding.CategoryBottomSheetBinding
import com.example.finalproject.databinding.FragmentNoteCreateBinding
import com.example.finalproject.ui.SecondaryAdapterListener
import com.example.finalproject.ui.adapters.CategoriesBottomSheetAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class NoteCreateFragment : Fragment(), SecondaryAdapterListener {

    companion object {
        fun newInstance() = NoteCreateFragment()
    }

    private val viewModel:NoteCreateViewModel by viewModels { NoteCreateViewModel.Factory }
    private lateinit var binding: FragmentNoteCreateBinding
    private var isForCreate:Boolean = true
    private lateinit var adapter: CategoriesBottomSheetAdapter
    private var categoriesList:List<Category> = listOf()
    private var dialog:BottomSheetDialog? = null
    private var categoryId:Long? = null

    private var categoryTV:TextView? = null
    private var toolBar: Toolbar? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteCreateBinding.inflate(inflater, container, false)


        lifecycleScope.launch {
            viewModel.categories.collect{it -> categoriesChanges(it)}
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryTV = binding.topicName
        toolBar = binding.toolbar

        adapter = context?.let { CategoriesBottomSheetAdapter(categoriesList, this, it) }!!

        binding.saveButton.setOnClickListener {
            if(isForCreate){
                viewModel.createNote(binding.titleTV.text.toString(), binding.noteTV.text.toString(), categoryId)
            }
            else{
                viewModel.updateNote(binding.titleTV.text.toString(), binding.noteTV.text.toString(), categoryId)
            }
            view.findNavController().popBackStack()
        }

        val title = arguments?.getString("title")

        if (title != null) {
            binding.noteTV.visibility = View.GONE
            binding.titleTV.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            isForCreate = false
            viewModel.findNoteByTitle(title)
            viewModel.noteLive.observe(viewLifecycleOwner){
                if (it != null) {
                    binding.noteTV.visibility = View.VISIBLE
                    binding.titleTV.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.titleTV.setText(it.title)
                    binding.noteTV.setText(it.note)
                }
            }

            viewModel.categoryLive.observe(viewLifecycleOwner){
                if(it != null){
                    categoryTV!!.text = it.name
                    toolBar!!.backgroundTintList = ColorStateList.valueOf(it.color)
                }
            }
        }

        binding.topicName.setOnClickListener {
            bottomSheetDialogAppear()
        }

        binding.backButton.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }


    private fun categoriesChanges(categories: List<Category>){
        categoriesList = categories
        adapter.categoriesList = categories
    }


    private fun bottomSheetDialogAppear(){
        //val dialogView = layoutInflater.inflate(R.layout.category_bottom_sheet, null)
        val bottomSheetBinding = CategoryBottomSheetBinding.inflate(layoutInflater)
        val dialogView = bottomSheetBinding.root
        dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dialogView)
        with(bottomSheetBinding){
            chooseCategoryRV.adapter = adapter
        }
        dialog?.show()
    }


    override fun onSecondaryListItemClick(position: Int) {
        categoryTV?.text = categoriesList[position].name
        toolBar?.backgroundTintList = ColorStateList.valueOf(categoriesList[position].color)
        categoryId = categoriesList[position].id
        dialog?.dismiss()
    }


}