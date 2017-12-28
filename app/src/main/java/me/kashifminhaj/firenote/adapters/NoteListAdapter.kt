package me.kashifminhaj.firenote.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_note.view.*
import me.kashifminhaj.firenote.R
import me.kashifminhaj.firenote.models.Note

/**
 * Created by kashif on 27/12/17.
 */
class NoteListAdapter(var data: MutableList<Note>) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.bindNoteItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.card_note, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bindNoteItem(note: Note) {
            with(note) {
                itemView.titleView.text = note.title
                itemView.contentView.text = note.content
            }
        }
    }
}