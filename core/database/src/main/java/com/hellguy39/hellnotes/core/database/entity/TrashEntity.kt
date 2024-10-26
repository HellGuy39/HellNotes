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
package com.hellguy39.hellnotes.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hellguy39.hellnotes.core.model.repository.local.database.Note

const val TRASH_TABLE_NAME = "trash_table"

@Deprecated("Mark deleted notes with [isTrashed] param")
@Entity(tableName = TRASH_TABLE_NAME)
data class TrashEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val note: Note,
    val dateOfAdding: Long,
)
