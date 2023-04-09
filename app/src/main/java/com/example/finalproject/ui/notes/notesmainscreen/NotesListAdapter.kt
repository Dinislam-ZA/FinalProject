package com.example.finalproject.ui.notes.notesmainscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.finalproject.data.model.Note
import com.example.finalproject.databinding.NotesListItemBinding
import com.example.finalproject.ui.MyClickListener

class NotesListAdapter(var notesList: List<Note>, private val listener: MyClickListener, val context:Context): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NotesListItemBinding, private val listener: MyClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NotesListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return NoteViewHolder(binding, listener)
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