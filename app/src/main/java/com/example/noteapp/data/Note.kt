package com.example.noteapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Define entity class Note  with attributes title,description,timestamp and specify the table name "notesTable"
@Entity(tableName = "notesTable")
class Note(
    @ColumnInfo(name = "title") val noteTitle: String,
    @ColumnInfo(name = "description") val noteDescription: String,
    @ColumnInfo(name = "timestamp") val timeStamp: String
) {
    //make the id field as primary key
    @PrimaryKey(autoGenerate = true)
    var id = 0
}