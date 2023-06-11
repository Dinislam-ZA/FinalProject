package com.example.finalproject.ui.profile.userslistscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.data.model.UserVO
import com.example.finalproject.databinding.FragmentFriendsListBinding
import com.example.finalproject.databinding.FragmentUsersListBinding
import com.example.finalproject.ui.adapters.UserAdapterListener
import com.example.finalproject.ui.adapters.UsersListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UsersListFragment : Fragment(), UserAdapterListener {

    companion object {
        fun newInstance() = UsersListFragment()
    }

    private val viewModel: UsersListViewModel by viewModels { UsersListViewModel.Factory }
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersListAdapter
    var dialog: BottomSheetDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        val view = binding.root

        lifecycleScope.launch {
            viewModel.users.collect {
                it -> updateUsers(it)
            }
        }

        return view
    }

    private fun updateUsers(users: List<UserVO>){
        adapter.submitList(users)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsersListAdapter(requireContext(), this)
        binding.usersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.usersRv.adapter = adapter
    }

    override fun onAddButtonClick(position: Int) {
        val id = adapter.getItems()[position].id
        viewModel.sendFriendshipRequest(id)
    }


}