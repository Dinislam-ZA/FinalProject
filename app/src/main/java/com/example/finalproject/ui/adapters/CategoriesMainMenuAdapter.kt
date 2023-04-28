package com.example.finalproject.ui.adapters



import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.Category
import com.example.finalproject.databinding.CategoryItemBinding
import com.example.finalproject.databinding.CategoryMainMenuItemBinding
import com.example.finalproject.ui.SecondaryAdapterListener

class CategoriesMainMenuAdapter(var categoriesList: List<Category>, private val listener: SecondaryAdapterListener, val context:Context): RecyclerView.Adapter<CategoriesMainMenuAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: CategoryMainMenuItemBinding, private val listener: SecondaryAdapterListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)

        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onSecondaryListItemClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryMainMenuItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return CategoryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder){
            with(categoriesList[position]){
                binding.categoryName.text = name
                binding.cardView.backgroundTintList= ColorStateList.valueOf(color)

            }
        }
    }
}