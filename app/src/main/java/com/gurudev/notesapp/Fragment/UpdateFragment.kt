package com.gurudev.notesapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.gurudev.notesapp.Model.Note
import com.gurudev.notesapp.R
import com.gurudev.notesapp.ViewModel.NoteViewModel
import com.gurudev.notesapp.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment() {

    private lateinit var binding : FragmentUpdateBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteToUpdate: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(layoutInflater)

        binding.actionBar.toolbar.title = "Update Note"
        binding.actionBar.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        val args = requireArguments()
        noteToUpdate = Note(
            id = args.getInt("noteId", 0),
            title = args.getString("noteTitle", "") ?: "",
            content = args.getString("noteContent", "") ?: ""
        )


        binding.editTextNoteTitle.setText(noteToUpdate.title)
        binding.editTextNoteContent.setText(noteToUpdate.content)


        binding.update.setOnClickListener {
            val updatedTitle = binding.editTextNoteTitle.text.toString().trim()
            val updatedContent = binding.editTextNoteContent.text.toString().trim()

            if (updatedTitle.isEmpty() || updatedContent.isEmpty()) {
                Snackbar.make(view, "Please fill out all fields", Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                noteViewModel.updateNote(noteToUpdate.copy(title = updatedTitle, content = updatedContent))
                Snackbar.make(view, "Note Updated successfully", Snackbar.LENGTH_SHORT).show()
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