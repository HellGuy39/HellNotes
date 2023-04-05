package com.hellguy39.hellnotes.core.model

data class Checklist(
    val id: Long? = null,
    val name: String,
    val noteId: Long,
    val items: List<ChecklistItem>
) {
    companion object {

        fun initialInstance(
            name: String = "",
            noteId: Long = -1,
            items: List<ChecklistItem> = listOf()
        ) = Checklist(
            name = name,
            noteId = noteId,
            items = items
        )

    }
}