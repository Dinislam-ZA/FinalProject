package com.example.finalproject.ui.profile.friendslistscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.data.model.User
import com.example.finalproject.data.model.UserVO
import com.example.finalproject.databinding.FragmentFriendsListBinding
import com.example.finalproject.databinding.FragmentProfileMainBinding
import com.example.finalproject.ui.adapters.FriendAdapterListener
import com.example.finalproject.ui.adapters.FriendListAdapter
import com.example.finalproject.ui.adapters.FriendRequestsAdapterListener
import com.example.finalproject.ui.adapters.FriendsRequestsAdapter
import com.example.finalproject.ui.profile.profilemainscreen.ProfileMainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class FriendsListFragment : Fragment(), FriendRequestsAdapterListener, FriendAdapterListener,
    PopupMenu.OnMenuItemClickListener {

    companion object {
        fun newInstance() = FriendsListFragment()
    }

    private var popUpMenu: PopupMenu? = null

    private val viewModel: FriendsListViewModel by viewModels { FriendsListViewModel.Factory }
    private lateinit var binding: FragmentFriendsListBinding

    private lateinit var friendsListAdapter: FriendListAdapter
    private lateinit var requestsListAdapter: FriendsRequestsAdapter

    private var friendsList:List<UserVO> = listOf()
    private var friendshipRequestsList:List<UserVO> = listOf()

    private fun updateFriendsList(friends: List<UserVO>){
        friendsList = friends
        friendsListAdapter.submitList(friendsList)
    }

    private fun updateFriendshipRequestsList(requests: List<UserVO>){
        friendshipRequestsList = requests
        friendsListAdapter.submitList(friendshipRequestsList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsListBinding.inflate(inflater, container, false)
        val view = binding.root

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.friends.collect {
                updateFriendsList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.requests.collect {
                updateFriendshipRequestsList(it)
            }
        }

        return view    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAdapters()

        binding.addFriendButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_friendsListFragment_to_usersListFragment)
        }
    }

    private fun initializeAdapters() {
        friendsListAdapter = FriendListAdapter(requireContext(), this)
        binding.friendsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.friendsRv.adapter = friendsListAdapter
        requestsListAdapter = FriendsRequestsAdapter(requireContext(), this)
        binding.requestsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.requestsRv.adapter = requestsListAdapter
    }

    override fun onSeeMoreButtonClick(cardView: CardView,position: Int) {
        popUpAppear(cardView)
    }

    override fun onAccept(position: Int) {
        viewModel.acceptFriendship(friendshipRequestsList[position].id)
    }

    override fun onReject(position: Int) {
        viewModel.rejectFriendship(friendshipRequestsList[position].id)
    }

    private fun popUpAppear(cardView: CardView) {

        popUpMenu = context?.let { PopupMenu(it, cardView) }
        popUpMenu?.setOnMenuItemClickListener(this)
        popUpMenu?.inflate(R.menu.pop_up_friends)
        popUpMenu?.show()

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_friend){
            //viewModel.deleteNote(selectedNote!!)
            return true
        }
        return false
    }

}