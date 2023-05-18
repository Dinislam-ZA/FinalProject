package com.example.finalproject.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
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
    private var color: Int = Color.parseColor("#59A5FF")

    companion object {
        private const val TYPE_ADD_NOTE = 0
        private const val TYPE_NOTE = 1
    }

    fun setColor(newColor:Int?){
        if(newColor!=null) color = newColor
        notifyDataSetChanged()
    }

    fun submitList(newItems: List<Note>) {
        val diffCallback = NotesInTasksDiffUtil(newItems, items)
        items = newItems
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NotesInTasksViewHolder(val binding: NotesInTaskListItemBinding, private val listener: OnNoteInTaskClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener{
        init {
            binding.cardView.backgroundTintList = ColorStateList.valueOf(color)
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onNoteClick(position)
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onNoteLongClick(position, binding.cardView)
                return true
            }
            return false
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

    fun onNoteLongClick(position: Int, cardView: CardView)
    fun onAddNoteClick()
}