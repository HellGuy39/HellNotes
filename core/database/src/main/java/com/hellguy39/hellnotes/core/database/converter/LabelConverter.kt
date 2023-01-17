package com.hellguy39.hellnotes.core.database.converter

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class LabelConverter {

    private val moshi = Moshi.Builder().build()
    private val type: ParameterizedType =
        Types.newParameterizedType(List::class.java, Long::class.javaObjectType)
    private val jsonAdapter: JsonAdapter<List<Long>> = moshi.adapter(type)

    @TypeConverter
    fun fromLabelList(list: List<Long>?): String? {
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toLabelList(jsonStr: String?): List<Long>? {
        return jsonStr?.let { jsonAdapter.fromJson(jsonStr) }
    }
}