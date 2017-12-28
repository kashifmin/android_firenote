package me.kashifminhaj.firenote.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_note.*
import me.kashifminhaj.firenote.R
import me.kashifminhaj.firenote.models.Note

class EditNoteActivity : AppCompatActivity() {
    companion object {
        val EXTRA_NOTE = "note"
    }

    lateinit var note: Note
    lateinit var mDb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)
        if (!intent.hasExtra(EXTRA_NOTE)) finish()

        note = intent.getSerializableExtra(EXTRA_NOTE) as Note
        noteTitle.text.append(note.title)
        noteContent.text.append(note.content)

        mDb = FirebaseDatabase.getInstance().reference
        saveBtn.setOnClickListener { save() }
    }

    fun save() {
        // TODO: validate fields
        note.title = noteTitle.text.toString()
        note.content = noteContent.text.toString()

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val path = "users/" + uid + "/notes"
        val key = if (note.id.equals("")) mDb.child(path).push().key else note.id
        val childUpdates: MutableMap<String, Any> = HashMap()

        childUpdates[path + "/" + key] = note.toMap()
        mDb.updateChildren(childUpdates).addOnCompleteListener { onSaveComplete() }

    }

    fun onSaveComplete() {
        Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()
        finish()
    }



}
