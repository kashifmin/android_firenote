package me.kashifminhaj.firenote.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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

        adapter = NoteListAdapter(noteList)
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
        mDb.child(path ).addChildEventListener(object: ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, prevChildName: String?) {
                noteList += dataSnapshot?.getValue(Note::class.java)!!
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

        })

    }
}
