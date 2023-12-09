package com.hellguy39.hellnotes.domain.usecase

import com.hellguy39.hellnotes.core.domain.usecase.note.PostProcessNoteUseCase
import com.hellguy39.hellnotes.core.model.NoteDetailWrapper
import com.hellguy39.hellnotes.core.model.repository.local.database.Checklist
import com.hellguy39.hellnotes.core.model.repository.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.repository.local.database.Note
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PostProcessNoteUseCaseTest {
    private var postProcessNoteUseCase: PostProcessNoteUseCase? = null
    private lateinit var noteWrapper: NoteDetailWrapper
    private lateinit var postProcessedNoteWrapper: NoteDetailWrapper

    @Before
    fun setup() {
        postProcessNoteUseCase = PostProcessNoteUseCase()
        noteWrapper =
            NoteDetailWrapper(
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
            NoteDetailWrapper(
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
