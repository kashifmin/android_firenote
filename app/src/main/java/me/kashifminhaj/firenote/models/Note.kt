package me.kashifminhaj.firenote.models

import java.io.Serializable

/**
 * Created by kashif on 27/12/17.
 */
data class Note(var id: String = "", var title: String = "", var content: String = ""): Serializable {
    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map.put("title", title)
        map.put("content", content)
        return map
    }
}