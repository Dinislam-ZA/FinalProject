package com.example.finalproject.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id IN (:NoteIds)")
    fun loadAllByIds(NoteIds: IntArray): List<Note>

    @Query("SELECT * FROM notes_table WHERE title LIKE :title LIMIT 1")
    fun findByName(title: String): Note

    @Query("SELECT * FROM notes_table WHERE id LIKE :id LIMIT 1")
    fun findById(id:Long): Flow<Note>

    @Insert
    fun insertAll(vararg Notes: Note)

    @Insert
    fun insert(note:Note)

    @Delete
    fun delete(Note: Note)
}