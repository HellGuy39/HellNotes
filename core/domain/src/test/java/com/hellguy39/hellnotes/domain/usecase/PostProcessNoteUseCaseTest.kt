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
package com.hellguy39.hellnotes.domain.usecase

import com.hellguy39.hellnotes.core.domain.usecase.note.PostProcessNoteUseCase
import com.hellguy39.hellnotes.core.model.NoteWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PostProcessNoteUseCaseTest {
    private var postProcessNoteUseCase: PostProcessNoteUseCase? = null
    private lateinit var noteWrapper: NoteWrapper
    private lateinit var postProcessedNoteWrapper: NoteWrapper

    @Before
    fun setup() {
        postProcessNoteUseCase = PostProcessNoteUseCase()
        noteWrapper =
            NoteWrapper(
                note =
                    Note(
                        title = "  title  ",
                        note = "  note  ",
                        createdAt = 0,
                        editedAt = 0,
                    ),
                checklists =
                    listOf(
                        Checklist(
                            name = "  name  ",
                            noteId = 0,
                            items =
                                listOf(
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "  item  ",
                                    ),
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "  item  ",
                                    ),
                                ),
                            isExpanded = false,
                        ),
                        Checklist(
                            name = "  name  ",
                            noteId = 1,
                            items =
                                listOf(
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "  item  ",
                                    ),
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "  item  ",
                                    ),
                                ),
                            isExpanded = false,
                        ),
                    ),
            )
        postProcessedNoteWrapper =
            NoteWrapper(
                note =
                    Note(
                        title = "title",
                        note = "note",
                        createdAt = 0,
                        editedAt = 0,
                    ),
                checklists =
                    listOf(
                        Checklist(
                            name = "name",
                            noteId = 0,
                            items =
                                listOf(
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "item",
                                    ),
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "item",
                                    ),
                                ),
                            isExpanded = false,
                        ),
                        Checklist(
                            name = "name",
                            noteId = 1,
                            items =
                                listOf(
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "item",
                                    ),
                                    ChecklistItem(
                                        isChecked = false,
                                        text = "item",
                                    ),
                                ),
                            isExpanded = false,
                        ),
                    ),
            )
    }

    @After
    fun cleanup() {
        postProcessNoteUseCase = null
    }

    @Test
    fun isNoteWrapperPostProcessCorrect() {
        val testeableNoteWrapper = postProcessNoteUseCase?.invoke(noteWrapper)

        Assert.assertEquals(testeableNoteWrapper, postProcessedNoteWrapper)
    }
}
