package com.gurudev.notesapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gurudev.notesapp.AppDatabase
import com.gurudev.notesapp.Model.Note
import com.gurudev.notesapp.Respository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository

    init {
        val noteDao = AppDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.allNotes
    }

    fun saveNote(title: String, content: String) {
        val note = Note(
            title = title,
            content = content
        )
        GlobalScope.launch(Dispatchers.IO) {
            repository.insert(note)
        }
    }


    fun deleteNote(note: Note) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.delete(note)
        }
    }

    fun updateNote(note: Note) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.update(note)
        }
    }
}
