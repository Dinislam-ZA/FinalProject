package com.example.finalproject.ui.notes.notesmainscreen

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.common.BaseAdapter
import com.example.finalproject.common.DiffUtilCallBackCommon
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.databinding.NotesListItemBinding
import com.example.finalproject.ui.MenuAdapterListener

//class NotesListAdapter(): BaseAdapter<Note, NotesListItemBinding>(){
//    override fun getLayoutResId(position: Int): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBind(binding: NotesListItemBinding, item: Note) {
//        TODO("Not yet implemented")
//    }
//
//}




class NotesListAdapter(private var notesList: List<Note>, private val listener: MenuAdapterListener, val context:Context): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {


    private val colorCache = HashMap<Long?, Int>()
    private var categoryList: List<Category> = listOf()

    fun setCategoryList(categories:List<Category>){
        categoryList = categories
    }

    fun setNotesList(newNotesList: List<Note>){
        val diffCallback = NotesDiffCallback(newNotesList, notesList)
        notesList = newNotesList
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun getCategoryById(id: Long?): Category? {
        if (id == null){
            return null
        }
        return categoryList.find { el -> el.id == id }
    }

    private fun getCategoryColor(categoryId: Long?): Int {
        if(categoryId != null){
            if (colorCache.containsKey(categoryId)) {
                // Возврат кэшированного цвета
                return colorCache[categoryId]!!
            } else {
                // Получение цвета из категории
                val category = getCategoryById(categoryId)
                val color = category?.color ?: return Color.parseColor("#59A5FF")
                colorCache[categoryId] = color
                return color
            }
        }
        return Color.parseColor("#59A5FF")
    }

    inner class NoteViewHolder(val binding: NotesListItemBinding, private val listener: MenuAdapterListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener{

        init {
            binding.cardView.setOnClickListener(this)
            binding.cardView.setOnLongClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(view: View?): Boolean {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onDelete(position, binding.cardView)
                return true
            }
            return false
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
                val color = getCategoryColor(categorie)
                binding.cardView.backgroundTintList = ColorStateList.valueOf(color)


            }
        }
    }
}


class NotesDiffCallback(private val newList:List<Note>, private val oldList:List<Note>):DiffUtil.Callback(){
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