package com.gurudev.notesapp.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.gurudev.notesapp.Fragment.AddNoteFragment
import com.gurudev.notesapp.Fragment.UpdateFragment
import com.gurudev.notesapp.MainActivity
import com.gurudev.notesapp.Model.Note
import com.gurudev.notesapp.R

class NoteAdapter internal constructor(private val activity: FragmentActivity,private val context: Context , private val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteTitle.text = current.title
        holder.noteContent.text = current.content
        holder.delete.setOnClickListener {
            onDeleteClickListener.onDeleteClickListener(current)
        }
        holder.update.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("noteId", current.id)
                putString("noteTitle", current.title)
                putString("noteContent", current.content)
            }

            val fragment = UpdateFragment()
            fragment.arguments = bundle

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.textNoteTitle)
        val noteContent: TextView = itemView.findViewById(R.id.textNoteContent)
        val delete: TextView = itemView.findViewById(R.id.delete)
        val update: TextView = itemView.findViewById(R.id.update)
    }

    interface OnDeleteClickListener {
        fun onDeleteClickListener(note: Note)
    }
}
