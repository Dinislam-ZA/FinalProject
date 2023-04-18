package com.example.finalproject.ui.profile.categoriesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.repositories.CategoryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class CategoriesViewModel(private val categoryRepo: CategoryRepo) : ViewModel() {

    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

    fun createCategory(name:String, color: Int){
        viewModelScope.launch(Dispatchers.IO) {
            if (name.isNotBlank()&& name.isNotEmpty()){
                val category = Category(null , name, color)
                categoryRepo.insertCategory(category)
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras


                return CategoriesViewModel(
                    CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}