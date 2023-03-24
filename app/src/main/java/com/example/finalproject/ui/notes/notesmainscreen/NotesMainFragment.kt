package com.example.finalproject.ui.notes.notesmainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentNotesMainBinding

class NotesMainFragment : Fragment() {

    companion object {
        fun newInstance() = NotesMainFragment()
    }

    private lateinit var viewModel: NotesMainViewModel
    private lateinit var binding: FragmentNotesMainBinding
    private lateinit var adapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesMainBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addNoteButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_notesMainFragment_to_noteCreateFragment)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}