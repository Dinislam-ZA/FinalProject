package com.example.finalproject.ui.auth.registerscreen

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
import com.example.finalproject.data.model.User
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.databinding.FragmentRegisterBinding
import com.example.finalproject.ui.auth.loginscreen.LoginViewModel

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory }
    private lateinit var binding: FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val toastSuccessRequest = Toast.makeText(requireContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT)


        viewModel.isOk.observe(viewLifecycleOwner) {
                it -> if(it == true){
                    toastSuccessRequest.show()
                    view?.findNavController()?.popBackStack()
                }

        }

        val toastBadRequest = Toast.makeText(requireContext(), "Не удалось выполнить запрос", Toast.LENGTH_SHORT)

        viewModel.isBad.observe(viewLifecycleOwner) {
                it -> if(it == true) {
                toastBadRequest.show()
                stopLoading()
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.regRegisterButton.setOnClickListener {
            onRegButtonClick()
        }
    }

    private fun onRegButtonClick(){
        isLoading()
        val username = binding.regUsernameInput.text.toString()
        val password = binding.regPasswordInput.text.toString()
        val email = binding.regPasswordRepeatInput.text.toString()
        val repeatPass = binding.regEmailInput.text.toString()
        viewModel.tryRegistration(username,password, repeatPass, email)
    }

    private fun isLoading(){
        binding.regProgressBar.visibility = View.VISIBLE
    }

    private fun stopLoading(){
        binding.regProgressBar.visibility = View.GONE
    }

}