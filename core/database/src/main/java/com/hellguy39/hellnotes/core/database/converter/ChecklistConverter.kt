/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.database.converter

import androidx.room.TypeConverter
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

class ChecklistConverter {
    private val moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val type: ParameterizedType =
        Types.newParameterizedType(
            List::class.java,
            ChecklistItem::class.javaObjectType,
        )

    private val jsonAdapter: JsonAdapter<List<ChecklistItem>> = moshi.adapter(type)

    @TypeConverter
    fun fromChecklist(list: List<ChecklistItem>?): String? {
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun toChecklist(jsonStr: String?): List<ChecklistItem>? {
        return jsonStr?.let { jsonAdapter.fromJson(jsonStr) }
    }
}
