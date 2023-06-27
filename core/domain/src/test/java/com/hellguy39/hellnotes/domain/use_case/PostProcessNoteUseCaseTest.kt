package com.hellguy39.hellnotes.domain.use_case

import com.hellguy39.hellnotes.core.domain.use_case.note.PostProcessNoteUseCase
import com.hellguy39.hellnotes.core.model.local.database.Checklist
import com.hellguy39.hellnotes.core.model.local.database.ChecklistItem
import com.hellguy39.hellnotes.core.model.local.database.Note
import com.hellguy39.hellnotes.core.model.NoteWrapper
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
        noteWrapper = NoteWrapper(
            note = Note(
                title = "  title  ",
                note = "  note  ",
                createdAt = 0,
                editedAt = 0
            ),
            checklists = listOf(
                Checklist(
                    name = "  name  ",
                    noteId = 0,
                    items = listOf(
                        ChecklistItem(
                            isChecked = false,
                            text = "  item  "
                        ),
                        ChecklistItem(
                            isChecked = false,
                            text = "  item  "
                        )
                    )
                ),
                Checklist(
                    name = "  name  ",
                    noteId = 1,
                    items = listOf(
                        ChecklistItem(
                            isChecked = false,
                            text = "  item  "
                        ),
                        ChecklistItem(
                            isChecked = false,
                            text = "  item  "
                        )
                    )
                )
            )
        )
        postProcessedNoteWrapper = NoteWrapper(
            note = Note(
                title = "title",
                note = "note",
                createdAt = 0,
                editedAt = 0
            ),
            checklists = listOf(
                Checklist(
                    name = "name",
                    noteId = 0,
                    items = listOf(
                        ChecklistItem(
                            isChecked = false,
                            text = "item"
                        ),
                        ChecklistItem(
                            isChecked = false,
                            text = "item"
                        )
                    )
                ),
                Checklist(
                    name = "name",
                    noteId = 1,
                    items = listOf(
                        ChecklistItem(
                            isChecked = false,
                            text = "item"
                        ),
                        ChecklistItem(
                            isChecked = false,
                            text = "item"
                        )
                    )
                )
            )
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