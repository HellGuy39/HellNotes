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
package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed class NoteSwipe(val tag: String) {
    data object None : NoteSwipe(NONE)

    data object Delete : NoteSwipe(DELETE)

    data object Archive : NoteSwipe(ARCHIVE)

    companion object {
        const val NONE = "none"
        const val DELETE = "delete"
        const val ARCHIVE = "archive"

        fun swipes() = listOf(None, Delete, Archive)

        fun default() = None

        fun defaultSwipeRight() = Delete

        fun defaultSwipeLeft() = Archive

        fun fromTag(
            tag: String?,
            defaultValue: NoteSwipe = None,
        ) = when (tag) {
            NONE -> None
            DELETE -> Delete
            ARCHIVE -> Archive
            else -> defaultValue
        }
    }
}
