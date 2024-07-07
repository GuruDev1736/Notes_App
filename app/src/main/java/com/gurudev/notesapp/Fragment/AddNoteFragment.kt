package com.gurudev.notesapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gurudev.notesapp.R
import com.gurudev.notesapp.ViewModel.NoteViewModel
import com.gurudev.notesapp.databinding.FragmentAddNoteBinding

class AddNoteFragment : Fragment() {

    private lateinit var binding : FragmentAddNoteBinding
    private lateinit var addEditNoteViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)

        binding.actionBar.toolbar.title = "Add Note"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addEditNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.btnSaveNote.setOnClickListener {
            val title = binding.editTextNoteTitle.text.toString().trim()
            val content = binding.editTextNoteContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Snackbar.make(view, "Please fill out all fields", Snackbar.LENGTH_SHORT).show()
            } else {
                addEditNoteViewModel.saveNote(title, content)
                Snackbar.make(view, "Note saved successfully", Snackbar.LENGTH_SHORT).show()
                navigateBack()
            }
        }
    }

    private fun navigateBack() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
    }

}