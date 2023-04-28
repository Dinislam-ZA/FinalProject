package com.example.finalproject.ui.profile.categoriesscreen

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.data.model.Category
import com.example.finalproject.databinding.FragmentCategoriesBinding
import com.example.finalproject.ui.MenuAdapterListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

class CategoriesFragment : Fragment(), MenuAdapterListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private val viewModel: CategoriesViewModel by viewModels { CategoriesViewModel.Factory }
    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var adapter: CategoriesListAdapter
    private var categoriesList:List<Category> = listOf()
    var dialog:BottomSheetDialog? = null

    private var categoryColor = 0x59A5FF
    private lateinit var colorChooseView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = context?.let {
            CategoriesListAdapter(
                categoriesList,
                this,
                it
            )
        }!!
        binding.categoriesRv.adapter = adapter

        binding.backButton.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.addCategoryButton.setOnClickListener {
            bottomSheetDialogAppear()
        }
        lifecycleScope.launch {
            viewModel.categories.collect{it -> categoriesChanges(it)}
        }

    }

    private fun categoriesChanges(categories: List<Category>){
        categoriesList = categories
        adapter.categoriesList = categories
        adapter.notifyDataSetChanged()
    }


    private fun bottomSheetDialogAppear(){
        val dialogView = layoutInflater.inflate(R.layout.category_create_dialog, null)
        dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dialogView)
        colorChooseView = dialogView.findViewById(R.id.color_choose_view)
        val categoryNameEditText = dialogView.findViewById<EditText>(R.id.category_name)
        val createCategoryButton = dialogView.findViewById<Button>(R.id.create_category_button)
        createCategoryButton.setOnClickListener {
            viewModel.createCategory(categoryNameEditText.text.toString(), categoryColor)
            dialog?.dismiss()
        }
        colorChooseView.setOnClickListener {
            colorPickDialogAppear(it)
        }
        dialog?.show()
    }


    private fun colorPickDialogAppear(view: View) {
        val dialogColor = AmbilWarnaDialog(context, 0x59A5FF, ColorPickListener(view))
        dialogColor.show()
    }

    inner class ColorPickListener(val view: View): OnAmbilWarnaListener{
        override fun onCancel(dialog: AmbilWarnaDialog?) {

        }

        override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
            colorChooseView.backgroundTintList = ColorStateList.valueOf(color)
            categoryColor = color
        }

    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDelete(position: Int, cardView: CardView) {
        TODO("Not yet implemented")
    }


}