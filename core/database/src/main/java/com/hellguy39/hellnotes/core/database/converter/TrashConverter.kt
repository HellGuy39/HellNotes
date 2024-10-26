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
