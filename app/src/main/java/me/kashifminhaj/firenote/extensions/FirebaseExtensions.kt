package me.kashifminhaj.firenote.extensions

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

/**
 * Created by kashif on 28/12/17.
 */
fun DatabaseReference.addChildListener(
        onAdded: (DataSnapshot?, String?) -> Unit,
        onRemoved: (DataSnapshot?) -> Unit = { },
        onChanged: (DataSnapshot?, String?) -> Unit = { p1, p2 -> },
        onMoved: (DataSnapshot?, String?) -> Unit = { p1, p2 -> },
        onCancel: (DatabaseError?) -> Unit = { }
) {
    this.addChildEventListener(object: ChildEventListener {
        override fun onCancelled(p0: DatabaseError?) = onCancel(p0)
        override fun onChildMoved(p0: DataSnapshot?, p1: String?) = onMoved(p0, p1)
        override fun onChildChanged(p0: DataSnapshot?, p1: String?) = onChanged(p0, p1)
        override fun onChildAdded(p0: DataSnapshot?, p1: String?) = onAdded(p0, p1)
        override fun onChildRemoved(p0: DataSnapshot?) = onRemoved(p0)

    })
}