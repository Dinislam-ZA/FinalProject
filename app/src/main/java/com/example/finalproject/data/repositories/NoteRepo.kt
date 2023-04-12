package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class NoteRepo(val context:Context) {
    private val db = AppDatabase.getDatabase(context)
    private val dao = db.noteDao()



    suspend fun insertNote(note: Note) = dao.insert(note)

    suspend fun updateNote(note: Note) = dao.update(note)

    suspend fun deleteNote(note: Note) = dao.delete(note)

    fun getAllNotes():Flow<List<Note>> = dao.getAll()

    suspend fun findNoteByTitle(title:String):Note = dao.findByName(title)
}