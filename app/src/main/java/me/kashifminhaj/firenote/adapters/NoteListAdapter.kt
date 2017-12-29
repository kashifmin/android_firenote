package me.kashifminhaj.firenote.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.card_note.view.*
import me.kashifminhaj.firenote.R
import me.kashifminhaj.firenote.models.Note

/**
 * Created by kashif on 27/12/17.
 */
class NoteListAdapter(options: FirebaseRecyclerOptions<Note>, val onItemClick: (Note) -> Unit) : FirebaseRecyclerAdapter<Note, NoteListAdapter.ViewHolder>(options) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int, note: Note) {
        note.id = this.getRef(position).key
        holder.bindNoteItem(note)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.card_note, parent, false)
        return ViewHolder(v, onItemClick)
    }

    class ViewHolder(v: View, val onItemClick: (Note) -> Unit): RecyclerView.ViewHolder(v) {
        fun bindNoteItem(note: Note) {
            with(note) {
                itemView.titleView.text = note.title
                itemView.contentView.text = note.content
            }
            itemView.setOnClickListener { onItemClick(note)  }
        }
    }
}