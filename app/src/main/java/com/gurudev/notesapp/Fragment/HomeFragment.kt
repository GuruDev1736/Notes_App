package com.gurudev.notesapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.gurudev.notesapp.Adapter.NoteAdapter
import com.gurudev.notesapp.Model.Note
import com.gurudev.notesapp.R
import com.gurudev.notesapp.ViewModel.NoteViewModel
import com.gurudev.notesapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteAdapter = NoteAdapter(requireActivity(),requireContext(), object : NoteAdapter.OnDeleteClickListener {
            override fun onDeleteClickListener(note: Note) {
                deleteNoteAndUpdateList(note)
            }
        })


        binding.notesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        observeViewModel()

        binding.fabAddNote.setOnClickListener {
            navigateToAddNote()
        }
    }

    private fun observeViewModel() {
        noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            notes?.let {
                noteAdapter.setNotes(it)
            }
        }
    }

    private fun navigateToAddNote() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AddNoteFragment())
            .commit()
    }

    private fun deleteNoteAndUpdateList(note: Note) {
        noteViewModel.deleteNote(note)
        Snackbar.make(requireView(), "Note deleted successfully", Snackbar.LENGTH_SHORT).show()
    }
}