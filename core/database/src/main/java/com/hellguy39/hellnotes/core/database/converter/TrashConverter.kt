package com.hellguy39.hellnotes.core.database.converter

import androidx.room.TypeConverter
import com.hellguy39.hellnotes.core.model.Note
import com.squareup.moshi.Moshi

class TrashConverter {

    private val moshi = Moshi.Builder().build()
    private val jsonAdapter = moshi.adapter(Note::class.java)

    @TypeConverter
    fun fromTrashNote(note: Note): String? {
        return jsonAdapter.toJson(note)
    }

    @TypeConverter
    fun toTrashNote(jsonStr: String): Note? {
        return jsonAdapter.fromJson(jsonStr)
    }

}