package com.example.finalproject.ui.notes.notecreatescreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class NoteCreateFragment : Fragment() {

    companion object {
        fun newInstance() = NoteCreateFragment()
    }

    private lateinit var viewModel: NoteCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_create, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteCreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}