package com.example.finalproject.common

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finalproject.data.model.Note

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Note>(key)

fun Fragment.setNavigationResult(result: Note, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}