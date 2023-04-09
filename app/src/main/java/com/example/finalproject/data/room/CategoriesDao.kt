package com.example.finalproject.data.room

import androidx.room.*
import com.example.finalproject.data.model.Categorie
import com.example.finalproject.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM Categories_table")
    fun getAll(): Flow<List<Categorie>>

    @Query("SELECT * FROM Categories_table WHERE id IN (:categorieIds)")
    fun loadAllByIds(categorieIds: IntArray): List<Categorie>

    @Query("SELECT * FROM Categories_table WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Categorie

    @Query("SELECT * FROM Categories_table WHERE id LIKE :id LIMIT 1")
    fun findById(id:Long): Flow<Categorie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg Notes: Categorie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categorie: Categorie)

//    @Update
//    suspend fun update(title: String, note: Note)

    @Delete
    fun delete(categorie: Categorie)
}