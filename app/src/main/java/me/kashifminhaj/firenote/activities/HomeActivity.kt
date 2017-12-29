package me.kashifminhaj.firenote.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import me.kashifminhaj.firenote.R
import me.kashifminhaj.firenote.adapters.NoteListAdapter
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


        loadNotes()
    }

    fun addNote() {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(EditNoteActivity.EXTRA_NOTE, Note())
        startActivity(intent)
    }

    fun loadNotes() {
        val path = "users/" + FirebaseAuth.getInstance().currentUser?.uid + "/notes"
        val query = mDb.child(path).limitToFirst(20)
        val options = FirebaseRecyclerOptions.Builder<Note>()
                .setQuery(query, Note::class.java)
                .build()
        adapter = NoteListAdapter(options, this::editNote)
        noteListRecycler.layoutManager = LinearLayoutManager(this)
        noteListRecycler.adapter = adapter

    }

    fun editNote(note: Note) {
        val i = Intent(this, EditNoteActivity::class.java)
        i.putExtra(EditNoteActivity.EXTRA_NOTE, note)
        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
