package com.example.finalproject.ui.notes.notecreatescreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentNoteCreateBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        binding.topicName.setOnClickListener {
            bottomSheetDialogAppear()
        }

        binding.backButton.setOnClickListener {
            view.findNavController().popBackStack()
        }

        val title = arguments?.getString("title")

        if (title != null) {
            Log.d("title", title)
            isForCreate = false
            viewModel.findNoteByTitle(title)
            viewModel.noteLive.observe(viewLifecycleOwner){
                if (it != null) {
                    binding.titleTV.setText(it.title)
                    binding.noteTV.setText(it.note)
                }
            }

        }
        else
        {
            Log.d("title", "no title(")
        }
        return binding.root
    }


    private fun bottomSheetDialogAppear(){
        val dialogView = layoutInflater.inflate(R.layout.category_bottom_sheet, null)
        val dialog = context?.let { BottomSheetDialog(it) }
        dialog?.setContentView(dialogView)
        dialog?.show()
    }


}