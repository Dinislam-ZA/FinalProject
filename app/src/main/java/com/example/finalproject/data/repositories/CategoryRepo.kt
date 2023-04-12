package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

class CategoryRepo(val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val dao = db.categoriesDao()

    suspend fun insertCategory(category: Category) = dao.insert(category)

    suspend fun deleteCategory(category: Category) = dao.delete(category)

    fun getAllCategories(): Flow<List<Category>> = dao.getAll()


}