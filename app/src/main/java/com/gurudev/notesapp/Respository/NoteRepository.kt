package com.gurudev.notesapp.Respository

import androidx.lifecycle.LiveData
import com.gurudev.notesapp.Model.Note
import com.gurudev.notesapp.Model.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }
}
