package com.example.finalproject.ui

import androidx.cardview.widget.CardView

interface MenuAdapterListener {
    fun onItemClick(position:Int)

    fun onDelete(position:Int, cardView: CardView)

}

interface SecondaryAdapterListener{
    fun onSecondaryListItemClick(position:Int)
}

interface SubTaskItemsListener{
    fun onSubTaskTextChanged(text:String ,position:Int)

    fun onSubTaskStatusChanged(isChecked: Boolean ,position:Int)

    fun onSubTaskMoved(fromPos: Int, toPos: Int)

    fun deleteSubTask(position: Int)
}



