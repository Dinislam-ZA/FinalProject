package com.example.finalproject.data.room

import androidx.room.*
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM Categories_table")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM Categories_table WHERE id IN (:categoryIds)")
    fun loadAllByIds(categoryIds: IntArray): List<Category>

    @Query("SELECT * FROM Categories_table WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Category

    @Query("SELECT * FROM Categories_table WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id:Long): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg Notes: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)


    @Delete
    suspend fun delete(category: Category)
}