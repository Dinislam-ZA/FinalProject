package com.example.finalproject.ui.profile.profilemainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
    }


    private fun bottomSheetDialogAppear(){
        val dialogView = layoutInflater.inflate(R.layout.fragment_login, null)
        dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dialogView)
        dialog?.show()
    }

    fun userAuthorized(username: String){
        binding.loginButton.visibility = View.GONE
        binding.logoutButton.visibility = View.VISIBLE
        binding.userNameTV.visibility = View.VISIBLE
        binding.userNameTV.text = username
    }


}