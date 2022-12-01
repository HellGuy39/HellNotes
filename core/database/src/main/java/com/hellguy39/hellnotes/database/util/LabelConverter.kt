package com.hellguy39.hellnotes.database.util

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class LabelConverter {

    private val moshi = Moshi.Builder().build()
    private val type: ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
    private val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)

    @TypeConverter
    fun fromLabelList(listMyModel: List<String>?): String? {
        return jsonAdapter.toJson(listMyModel)
    }

    @TypeConverter
    fun toLabelList(jsonStr: String?): List<String>? {
        return jsonStr?.let { jsonAdapter.fromJson(jsonStr) }
    }
}