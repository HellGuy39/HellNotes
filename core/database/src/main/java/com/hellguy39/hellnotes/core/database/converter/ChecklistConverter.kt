package com.hellguy39.hellnotes.core.database.converter

import androidx.room.TypeConverter
import com.hellguy39.hellnotes.core.model.CheckItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

class ChecklistConverter {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type: ParameterizedType = Types.newParameterizedType(
        List::class.java, CheckItem::class.javaObjectType
    )

    private val jsonAdapter: JsonAdapter<List<CheckItem>> = moshi.adapter(type)

    @TypeConverter
    fun fromChecklist(list: List<CheckItem>?): String? {
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toChecklist(jsonStr: String?): List<CheckItem>? {
        return jsonStr?.let { jsonAdapter.fromJson(jsonStr) }
    }
}