package com.example.noteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define the database class with RoomDatabase annotation
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    // Declare an abstract method to get the DAO (Data Access Object)
    abstract fun getNotesDao(): NotesDAO

    // Define a companion object to implement Singleton pattern
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "noteTable"// Specify the database name
                ).build()
                // Save the instance to the INSTANCE variable and return it
                INSTANCE = instance
                instance
            }
        }
    }
}