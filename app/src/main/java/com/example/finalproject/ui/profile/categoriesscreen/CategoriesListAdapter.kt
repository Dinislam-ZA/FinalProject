package com.example.finalproject.ui.profile.categoriesscreen

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.Category
import com.example.finalproject.databinding.CategoryItemBinding
import com.example.finalproject.ui.MenuAdapterListener

class CategoriesListAdapter(var categoriesList: List<Category>, private val listener: CategoryAdapterListener, val context:Context): RecyclerView.Adapter<CategoriesListAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: CategoryItemBinding, private val listener: CategoryAdapterListener) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return CategoryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder){
            with(categoriesList[position]){
                binding.categoryName.text = name
                binding.cardViewLayout.backgroundTintList= ColorStateList.valueOf(color)
                binding.root.setOnClickListener {
                    if (binding.categoryHint.visibility == View.VISIBLE){
                        binding.categoryHint.visibility = View.GONE
                        binding.taskStatisticTv.visibility = View.VISIBLE
                        binding.taskProgressTv.visibility = View.VISIBLE
                        binding.taskActiveNumberTv.visibility = View.VISIBLE
                        binding.habitsStatisticTv.visibility = View.VISIBLE
                        binding.habitProgressTv.visibility = View.VISIBLE
                        binding.habitActiveNumberTv.visibility = View.VISIBLE
                    }
                    else{
                        binding.categoryHint.visibility = View.VISIBLE
                        binding.taskStatisticTv.visibility = View.GONE
                        binding.taskProgressTv.visibility = View.GONE
                        binding.taskActiveNumberTv.visibility = View.GONE
                        binding.habitsStatisticTv.visibility = View.GONE
                        binding.habitProgressTv.visibility = View.GONE
                        binding.habitActiveNumberTv.visibility = View.GONE
                    }

                }
            }
        }
    }
}

interface CategoryAdapterListener{
    fun onDeleteClick(position: Int, cardView: CardView)
}