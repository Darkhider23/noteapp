package com.example.noteapp.data

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDAO: NotesDAO) {
    //We hold the list of all notes in LiveData
    val allNotes:LiveData<List<Note>> = notesDAO.getAllNotes()
    //methods for insert delete update from DAO
    suspend fun insert(note:Note){
        notesDAO.insert(note)
    }
    suspend fun delete(note: Note){
        notesDAO.delete(note)
    }

    suspend fun update(note: Note){
        notesDAO.update(note)
    }
}