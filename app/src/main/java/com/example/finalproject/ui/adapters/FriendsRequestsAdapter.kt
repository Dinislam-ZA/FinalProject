package com.example.finalproject.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.model.UserVO
import com.example.finalproject.databinding.FriendsListItemBinding
import com.example.finalproject.databinding.FriendshipRequestListItemBinding


class FriendsRequestsAdapter(val context: Context, private val listener: FriendRequestsAdapterListener): RecyclerView.Adapter<FriendsRequestsAdapter.RequestViewHolder>() {

    private var items:List<UserVO> = listOf()

    fun submitList(newItems: List<UserVO>) {
        val diffCallback = UserDiffUtil(newItems, items)
        items = newItems
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class RequestViewHolder(val binding: FriendshipRequestListItemBinding, private val listener: FriendRequestsAdapterListener) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            binding.acceptButton.setOnClickListener(this)
            binding.unacceptButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                when(p0?.id){
                    binding.acceptButton.id -> listener.onAccept(position)
                    binding.unacceptButton.id -> listener.onReject(position)
                }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = FriendshipRequestListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return RequestViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        with(holder){
            with(items[position]){
                binding.usernameTv.text = username
            }

        }
    }
}

interface FriendRequestsAdapterListener {

    fun onAccept(position: Int)

    fun onReject(position: Int)
}