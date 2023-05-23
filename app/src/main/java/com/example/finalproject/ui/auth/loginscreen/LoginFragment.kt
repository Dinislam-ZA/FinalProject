package com.example.finalproject.ui.auth.loginscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.data.model.LoginRequest
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.ui.notes.notecreatescreen.NoteCreateViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }
    private lateinit var binding: FragmentLoginBinding

    //private val toastBadRequest = Toast.makeText(requireContext(), "Не удалось выполнить запрос", Toast.LENGTH_SHORT)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel.isOk.observe(viewLifecycleOwner) {
                it -> if(it == true) view?.findNavController()?.popBackStack()
        }

        viewModel.isBad.observe(viewLifecycleOwner) {
                it -> if(it == true) {
                //    toastBadRequest.show()
                    stopLoading()
                }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButtonAuth.setOnClickListener {
            loginButtonClick()
        }

    }

    fun loginButtonClick(){
        val username = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val userDetails = LoginRequest(username = username, password = password)
        isLoading()
        viewModel.loginUser(userDetails)
    }

    fun isLoading(){
        binding.progressBarLogin.visibility = View.VISIBLE
    }

    fun stopLoading(){
        binding.progressBarLogin.visibility = View.GONE
    }

}