package com.example.noteapp.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDAO {
    //Insert a note into the data
    @Insert(onConflict = OnConflictStrategy.IGNORE)//This ensures that if there are coflicts such as smae pk, the insertion will be ingnored
    suspend fun insert(note:Note)

    @Update
    suspend fun update(note:Note)

    @Delete
    suspend fun delete(note:Note)
    //Query to retrieve all records from database first ordered by timestamp then by title
    //// LiveData is used to observe changes to the notes in real-time.
    @Query("SELECT * FROM notesTable ORDER BY timestamp DESC, title ASC")
    fun getAllNotes():LiveData<List<Note>>

}