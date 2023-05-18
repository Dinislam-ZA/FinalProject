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
    fun onSubTaskChanged(text:String ,position:Int)

    fun deleteSubTask(position: Int)
}



