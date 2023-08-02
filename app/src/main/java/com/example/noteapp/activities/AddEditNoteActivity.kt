package com.example.noteapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.MainActivity
import com.example.noteapp.data.Note
import com.example.noteapp.viewmodels.NoteViewModel
import com.example.noteapp.R
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdt:EditText
    lateinit var addupdateBtn:Button
    lateinit var viewModal:NoteViewModel
    var noteID = -1

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        // Find views from the layout
        noteTitleEdt = findViewById(R.id.idEdtNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEdtNoteDescription)
        addupdateBtn = findViewById(R.id.idBtnAddUpdate)

        // Initialize the NoteViewModel
        viewModal = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        // Handle the back button click
        val backButton = findViewById<Button>(R.id.idBtnBack)
        backButton.setOnClickListener{
            Toast.makeText(this, "Note not saved.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AddEditNoteActivity, MainActivity::class.java)
            startActivity(intent)
        }
        // Get the note type (Add or Edit) from the intent
        val noteType = intent.getStringExtra("noteType")

        // If it's an Edit action, pre-fill the EditText fields with existing data
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID",-1)
            addupdateBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdt.setText(noteDesc)
        }else{
            addupdateBtn.setText("Save Note")
        }
        addupdateBtn.setOnClickListener{
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteDescriptionEdt.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty()&& noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM,yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    // Create a new Note object with updated data
                    val updateNote = Note(noteTitle,noteDescription,currentDate)
                    updateNote.id = noteID


//                    Log.d("UpdateNoteData", "Updating Note: Title=$noteTitle, Description=$noteDescription, Date=$currentDate, ID=$noteID")


                    // Update the note in the database using the NoteViewModel
                    viewModal.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated..", Toast.LENGTH_LONG).show()
                }
            }
                else{
                    if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                        val sdf = SimpleDateFormat("dd MMM,yyyy - HH:mm")
                        val currentDate:String = sdf.format(Date())

                        // Create a new Note object with the new data
                        viewModal.addNote(Note(noteTitle,noteDescription,currentDate))
                        Toast.makeText(this,"Note Added..",Toast.LENGTH_LONG).show()
                    }
                }
            // Start the MainActivity and finish this activity
                startActivity(Intent(applicationContext,MainActivity::class.java))
                this.finish()
            }
        }

    }
