package com.example.finalproject.ui.profile.categoriesscreen

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.data.model.Category
import com.example.finalproject.databinding.FragmentCategoriesBinding
import com.example.finalproject.ui.MenuAdapterListener
import com.example.finalproject.ui.adapters.ColorAdapter
import com.example.finalproject.ui.decoration.GridSpacingItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


class CategoriesFragment : Fragment(), CategoryAdapterListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private val viewModel: CategoriesViewModel by viewModels { CategoriesViewModel.Factory }
    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var adapter: CategoriesListAdapter
    private var categoriesList:List<Category> = listOf()
    private var dialog:BottomSheetDialog? = null

    private var categoryColor = 0x59A5FF
    private lateinit var colorChooseView: RecyclerView

    private val colors = listOf(
        Color.parseColor("#59A5FF"), Color.rgb(179, 157, 219), Color.rgb(4, 217, 146), Color.rgb(245, 245, 220), Color.parseColor("#07F0EA"),
        Color.rgb(169, 169, 169), Color.parseColor("#07F060"), Color.rgb(244, 164, 96), Color.parseColor("#E50096"), Color.parseColor("#EFE70D"),
        Color.rgb(255, 228, 181), Color.rgb(72, 61, 139), Color.rgb(205, 92, 92), Color.rgb(255, 165, 0), Color.rgb(102, 205, 170)
    )

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
            viewModel.categories.collect{ categoriesChanges(it)}
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


        val categoryNameEditText = dialogView.findViewById<EditText>(R.id.category_name)
        val createCategoryButton = dialogView.findViewById<Button>(R.id.create_category_button)


        val colorAdapter = ColorAdapter(requireContext(),colors) {selectedColor ->
            categoryColor = selectedColor
            createCategoryButton.setBackgroundColor(selectedColor)
            val drawable = categoryNameEditText.background as Drawable
            drawable.colorFilter = PorterDuffColorFilter(selectedColor, PorterDuff.Mode.SRC_IN)
            categoryNameEditText.background = drawable
        }
        colorChooseView = dialogView.findViewById(R.id.color_picker_view)
        colorChooseView.layoutManager = GridLayoutManager(requireContext(), 5)
        colorChooseView.addItemDecoration(GridSpacingItemDecoration(5, 15, true))
        colorChooseView.adapter = colorAdapter

        createCategoryButton.setOnClickListener {
            viewModel.createCategory(categoryNameEditText.text.toString(), categoryColor)
            dialog?.dismiss()
        }
        dialog?.show()
    }


    override fun onDeleteClick(position: Int, cardView: CardView) {
        TODO("Not yet implemented")
    }


}