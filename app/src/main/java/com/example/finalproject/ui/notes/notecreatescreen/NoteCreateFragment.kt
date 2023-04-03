package com.example.finalproject.ui.notes.notecreatescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.finalproject.databinding.FragmentNoteCreateBinding
import kotlinx.coroutines.launch

class NoteCreateFragment : Fragment() {

    companion object {
        fun newInstance() = NoteCreateFragment()
    }

    private val viewModel:NoteCreateViewModel by viewModels { NoteCreateViewModel.Factory }
    private lateinit var binding: FragmentNoteCreateBinding
    private var isForCreate:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteCreateBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.saveButton.setOnClickListener {
            viewModel.createNote(binding.titleTV.text.toString(), binding.noteTV.text.toString())
            view.findNavController().popBackStack()
        }

        binding.backButton.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val title = arguments?.getString("arg2")
        if (title != null) {
            var note = viewModel.findNoteByTitle(title)
            binding.titleTV.setText(note?.title)
            binding.noteTV.setText(note?.note)
        }
        return binding.root
    }



}