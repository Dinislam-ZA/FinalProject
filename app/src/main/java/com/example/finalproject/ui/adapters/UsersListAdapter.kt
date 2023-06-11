package com.example.finalproject.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.User
import com.example.finalproject.data.model.UserVO
import com.example.finalproject.databinding.SubtaskListItemBinding
import com.example.finalproject.databinding.UserListItemBinding
import com.example.finalproject.ui.SubTaskItemsListener
import java.util.*



class UserDiffUtil(private val newList:List<UserVO>, private val oldList:List<UserVO>): DiffUtil.Callback(){
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition].id
        val oldItem = oldList[oldItemPosition].id

        return newItem == oldItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]

        return newItem == oldItem
    }

}


class UsersListAdapter(val context: Context, private val listener: UserAdapterListener): RecyclerView.Adapter<UsersListAdapter.UserViewHolder>() {

    private var items:List<UserVO> = listOf()

    fun getItems() = items

    fun submitList(newItems: List<UserVO>) {
        val diffCallback = UserDiffUtil(newItems, items)
        items = newItems
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(val binding: UserListItemBinding, private val listener: UserAdapterListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.addFriendButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            binding.addFriendButton.setImageResource(R.drawable.baseline_check_24)
            binding.addFriendButton.isEnabled = false
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onAddButtonClick(position)
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return UserViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                binding.userNameTV.text = username
            }

        }
    }
}

interface UserAdapterListener {

    fun onAddButtonClick(position: Int)
}
