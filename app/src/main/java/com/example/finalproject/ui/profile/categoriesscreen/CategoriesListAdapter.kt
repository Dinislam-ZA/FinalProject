package com.example.finalproject.ui.profile.categoriesscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.databinding.CategoryItemBinding
import com.example.finalproject.databinding.NotesListItemBinding
import com.example.finalproject.ui.MyClickListener

class CategoriesListAdapter(var categoriesList: List<Category>, private val listener: MyClickListener, val context:Context): RecyclerView.Adapter<CategoriesListAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: CategoryItemBinding, private val listener: MyClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)

        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

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
                binding.cardViewLayout.setBackgroundColor(color)
            }
        }
    }
}