package com.example.finalproject.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.UserVO
import com.example.finalproject.databinding.FriendsListItemBinding
import com.example.finalproject.databinding.UserListItemBinding


class FriendListAdapter(val context: Context, private val listener: FriendAdapterListener): RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    private var items:List<UserVO> = listOf()

    fun submitList(newItems: List<UserVO>) {
        val diffCallback = UserDiffUtil(newItems, items)
        items = newItems
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FriendViewHolder(val binding: FriendsListItemBinding, private val listener: FriendAdapterListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.detailsButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onSeeMoreButtonClick(binding.cardViewFriend ,position)
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = FriendsListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return FriendViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                binding.usernameTv.text = username
            }

        }
    }
}

interface FriendAdapterListener {

    fun onSeeMoreButtonClick(cardView: CardView, position: Int)
}