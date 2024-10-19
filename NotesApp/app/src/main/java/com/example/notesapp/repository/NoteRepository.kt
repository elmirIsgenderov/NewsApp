package com.example.notesapp.repository

import com.example.notesapp.database.NoteDataBase
import com.example.notesapp.model.Note

class NoteRepository(private var db: NoteDataBase) {

    fun getAllNote() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().getSearchNote(query)

    suspend fun insert(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun update(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun delete(note: Note) = db.getNoteDao().delete(note)

}