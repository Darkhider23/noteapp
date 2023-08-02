package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.activities.AddEditNoteActivity
import com.example.noteapp.adapters.NoteClickInterface
import com.example.noteapp.adapters.NoteClickDeleteInterface
import com.example.noteapp.adapters.NoteRVAdapter
import com.example.noteapp.data.Note
import com.example.noteapp.viewmodels.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    lateinit var notesRV:RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var  viewModal: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //Hide actionbar
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        //Initialize RecyclerView and FloatingACtionButton
        notesRV=findViewById(R.id.idRVNotes)
        addFAB=findViewById(R.id.idFABAddNote)
        // Set RecyclerView layout manager
        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this,this,this)
        notesRV.adapter = noteRVAdapter
        // Observe the LiveData from ViewModel and update the RecyclerView's data accordingly
        viewModal = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModal.allNote.observe(this) { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        }
        // Set a click listener for the FloatingActionButton to add new notes
        addFAB.setOnClickListener{
            val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    // Callback function for the onDeleteIconClick from NoteRVAdapter
    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted",Toast.LENGTH_LONG).show()
    }
    // Callback function for the onNoteClick from NoteRVAdapter
    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteID",note.id)
        startActivity(intent)

    }
}