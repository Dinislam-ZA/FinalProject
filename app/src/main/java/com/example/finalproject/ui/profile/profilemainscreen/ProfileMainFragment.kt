package com.example.finalproject.ui.profile.profilemainscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.data.sharedpreference.SecureStorage
import com.example.finalproject.databinding.FragmentProfileMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProfileMainFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileMainFragment()
    }

    private val viewModel:ProfileMainViewModel by viewModels { ProfileMainViewModel.Factory }
    private lateinit var binding: FragmentProfileMainBinding
    var dialog:BottomSheetDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val securityManager = SecureStorage(requireContext())
        if (securityManager.token != null){
            securityManager.username?.let { userAuthorized(it) }

        }


        binding.categoriesButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileMainFragment_to_categoriesFragment)
        }

        binding.loginButton.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.fragmentContainerView)
            navController.navigate(R.id.action_mainFragment_to_loginFragment2)
        }

        binding.logoutButton.setOnClickListener {
            userUnauthorized()
        }

        binding.friendListButton.setOnClickListener {
            if (securityManager.token != null){
                view.findNavController().navigate(R.id.action_profileMainFragment_to_friendsListFragment)
            }
            else{
                val toastAuthRequest = Toast.makeText(requireContext(), "Требуется авторизация", Toast.LENGTH_SHORT)
                toastAuthRequest.show()
            }
        }
    }




    private fun userAuthorized(username: String){
        binding.loginButton.visibility = View.GONE
        binding.logoutButton.visibility = View.VISIBLE
        binding.userNameTV.visibility = View.VISIBLE
        binding.userNameTV.text = username
    }

    private fun userUnauthorized() {
        binding.loginButton.visibility = View.VISIBLE
        binding.logoutButton.visibility = View.GONE
        binding.userNameTV.visibility = View.GONE
        val securityManager = SecureStorage(requireContext())
        securityManager.clearAll()
    }

}