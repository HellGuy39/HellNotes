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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class LabelConverter {
    private val moshi = Moshi.Builder().build()
    private val type: ParameterizedType =
        Types.newParameterizedType(
            List::class.java,
            Long::class.javaObjectType,
        )
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
