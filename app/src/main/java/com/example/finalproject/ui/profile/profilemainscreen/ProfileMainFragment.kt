package com.example.finalproject.ui.profile.profilemainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentProfileMainBinding

class ProfileMainFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileMainFragment()
    }

    private val viewModel:ProfileMainViewModel by viewModels { ProfileMainViewModel.Factory }
    private lateinit var binding: FragmentProfileMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileMainBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.categoriesButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileMainFragment_to_categoriesFragment)
        }
        return view
    }


}