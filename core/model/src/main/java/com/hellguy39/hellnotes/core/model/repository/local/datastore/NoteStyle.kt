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

import com.hellguy39.hellnotes.core.model.wrapper.Tagged
import com.hellguy39.hellnotes.core.model.wrapper.TaggedCompanion
import kotlinx.coroutines.flow.flow

sealed class NoteStyle(tag: String) : Tagged(tag, key) {
    data object Outlined : NoteStyle(OUTLINED)

    data object Elevated : NoteStyle(ELEVATED)

    companion object : TaggedCompanion<NoteStyle> {
        override val key = "note_style"

        override fun defaultValue() = Outlined

        override fun fromTag(tag: String?) = when (tag) {
            OUTLINED -> Outlined
            ELEVATED -> Elevated
            else -> defaultValue()
        }

        override fun values() = listOf(Outlined, Elevated)

        override fun valuesFlow() = flow { emit(values()) }

        private const val OUTLINED = "outlined"
        private const val ELEVATED = "elevated"
    }
}
