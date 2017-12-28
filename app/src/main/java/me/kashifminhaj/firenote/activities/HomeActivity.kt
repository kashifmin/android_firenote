package me.kashifminhaj.firenote.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import me.kashifminhaj.firenote.R
import me.kashifminhaj.firenote.adapters.NoteListAdapter
import me.kashifminhaj.firenote.extensions.addChildListener
import me.kashifminhaj.firenote.models.Note

class HomeActivity : AppCompatActivity() {
    lateinit var mDb: DatabaseReference
    val noteList: MutableList<Note> = ArrayList()
    lateinit var adapter: NoteListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addNoteFAB.setOnClickListener { addNote() }
        mDb = FirebaseDatabase.getInstance().reference

        adapter = NoteListAdapter(noteList, this::editNote)
        noteListRecycler.layoutManager = LinearLayoutManager(this)
        noteListRecycler.adapter = adapter

        loadNotes()
    }

    fun addNote() {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(EditNoteActivity.EXTRA_NOTE, Note())
        startActivity(intent)
    }

    fun loadNotes() {
        val path = "users/" + FirebaseAuth.getInstance().currentUser?.uid + "/notes"
        mDb.child(path ).addChildListener({
            dataSnapshot, prevChildName ->
            val note = dataSnapshot?.getValue(Note::class.java)!!
            note.id = dataSnapshot.key
            noteList += note
            adapter.notifyDataSetChanged()
        }, {
            dataSnapshot ->
            noteList.remove(dataSnapshot?.getValue(Note::class.java))
            adapter.notifyDataSetChanged()
        })

    }

    fun editNote(note: Note) {
        val i = Intent(this, EditNoteActivity::class.java)
        i.putExtra(EditNoteActivity.EXTRA_NOTE, note)
        startActivity(i)
    }
}
