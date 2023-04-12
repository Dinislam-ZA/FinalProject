package com.example.finalproject.data.room

import androidx.room.*
import com.example.finalproject.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id IN (:NoteIds)")
    fun loadAllByIds(NoteIds: IntArray): List<Note>

    @Query("SELECT * FROM notes_table WHERE title LIKE :title LIMIT 1")
    suspend fun findByName(title: String): Note

    @Query("SELECT * FROM notes_table WHERE id LIKE :id LIMIT 1")
    fun findById(id:Long): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg Notes: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Update
    suspend fun update(Note: Note)

    @Delete
    suspend fun delete(Note: Note)
}