package com.example.noteapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.noteapp.data.NoteRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    val allNote: LiveData<List<Note>>//live data which holds the notes
    val repository: NoteRepository// instance of repo

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)
        allNote = repository.allNotes
    }
    // Coroutine function to delete a note using the NoteRepository's delete method.
    // This function is executed on the IO dispatcher to perform database operations.
    fun deleteNote(note: Note) = viewModelScope.launch ( Dispatchers.IO ){
        repository.delete(note)
    }
    //Same thing as above but for update
    fun updateNote(note: Note) = viewModelScope.launch ( Dispatchers.IO ){
        repository.update(note)
    }
    //Same thing as above but for add
    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}