package com.example.noteapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.Note
import com.example.noteapp.R

class NoteRVAdapter(
    val context: Context,
    val noteClickInterface: NoteClickInterface,
    val noteClickdeleteInterface: NoteClickDeleteInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {
    //Arraylist to store the notes which will be displayed in the RecyclerView
    private val allNotes = ArrayList<Note>()

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Views inside the RecyclerView item layout
            val noteTV = itemView.findViewById<TextView>(R.id.idTVNoteTitle)
            val timeTV = itemView.findViewById<TextView>(R.id.idTVTimeStamp)
            val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
            val noteDesc = itemView.findViewById<TextView>(R.id.idTVNoteDesc)

        }
    // Function to create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)
        return ViewHolder(itemView)
    }
    // Function to get the total number of items in the RecyclerView
    override fun getItemCount(): Int {
        return allNotes.size
    }
    // Function to bind the data to the ViewHolder
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTV.setText(allNotes.get(position).noteTitle)
        holder.timeTV.setText(allNotes.get(position).timeStamp)
        holder.noteDesc.setText(allNotes.get(position).noteDescription)
        holder.deleteIV.setOnClickListener {
            noteClickdeleteInterface.onDeleteIconClick(allNotes.get(position))
        }
        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }
    // Function to update the list of notes in the RecyclerView and notify the adapter of changes
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    fun onNoteClick(note: Note)
}