package com.example.finalproject.ui.notes.notesmainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finalproject.data.model.Note
import com.example.finalproject.databinding.NotesListItemBinding

class NotesListAdapter(var notesList: List<Note>): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NotesListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        with(holder){
            with(notesList[position]){
                binding.title.text = title
                binding.note.text = note
                binding.date.text = createdAt
            }
        }
    }
}