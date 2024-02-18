package com.hellguy39.hellnotes.core.database.converter

import androidx.room.TypeConverter
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TrashConverter {
    private val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

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
