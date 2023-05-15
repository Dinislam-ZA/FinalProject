package com.example.finalproject.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.databinding.NoteAddViewholderBinding
import com.example.finalproject.databinding.NotesInTaskListItemBinding
import com.example.finalproject.databinding.NotesListItemBinding
import com.example.finalproject.databinding.SubtaskListItemBinding
import com.example.finalproject.ui.SubTaskItemsListener
import java.util.*


class NotesInTasksDiffUtil(private val newList:List<Note>, private val oldList:List<Note>): DiffUtil.Callback(){
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newNote = newList[newItemPosition].id
        val oldNote = oldList[oldItemPosition].id

        return newNote == oldNote
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newNote = newList[newItemPosition]
        val oldNote = oldList[oldItemPosition]

        return newNote == oldNote
    }

}


class NotesInTasksAdapter(val context: Context, private val listener: OnNoteInTaskClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items:List<Note> = listOf()
    private var color: Int = Color.BLUE

    companion object {
        private const val TYPE_ADD_NOTE = 0
        private const val TYPE_NOTE = 1
    }

    fun submitList(newItems: List<Note>) {
        val diffCallback = NotesInTasksDiffUtil(newItems, items)
        items = newItems
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NotesInTasksViewHolder(val binding: NotesInTaskListItemBinding, private val listener: OnNoteInTaskClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardView.backgroundTintList = ColorStateList.valueOf(color)
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onNoteClick(position)
                }
            }
        }
    }

    inner class AddNoteViewHolder(val binding: NoteAddViewholderBinding, private val listener: OnNoteInTaskClickListener) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.backgroundTintList = ColorStateList.valueOf(color)
            binding.root.setOnClickListener {
                listener.onAddNoteClick()
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_ADD_NOTE else TYPE_NOTE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ADD_NOTE) {
            val binding = NoteAddViewholderBinding.inflate(LayoutInflater.from(context), parent, false)
            AddNoteViewHolder(binding, listener)
        } else {
            val binding = NotesInTaskListItemBinding.inflate(LayoutInflater.from(context), parent, false)
            NotesInTasksViewHolder(binding, listener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotesInTasksViewHolder) {
            with(holder){
                with(items[position]){
                    binding.title.text = title
                    binding.note.text = note ?: "No note"
                    binding.date.text = createdAt
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface OnNoteInTaskClickListener {
    fun onNoteClick(position: Int)
    fun onAddNoteClick()
}