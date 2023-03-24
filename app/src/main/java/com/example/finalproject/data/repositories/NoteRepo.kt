package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

class NoteRepo(val context:Context) {
    val db = AppDatabase.getDatabase(context)
    val dao = db.noteDao()

    suspend fun insertNote(note: Note) = dao.insert(note)

    suspend fun getAllNotes():Flow<List<Note>> = dao.getAll()
}