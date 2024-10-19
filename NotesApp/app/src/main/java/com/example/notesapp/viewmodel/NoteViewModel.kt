package com.example.notesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.model.Note
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(val app: Application, private val noteRepository: NoteRepository) :
    AndroidViewModel(app) {

    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insert(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.delete(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.update(note)
        }

    fun getAllNotes() = noteRepository.getAllNote()

    fun searchNote(query: String?) =
        noteRepository.searchNote(query)

}