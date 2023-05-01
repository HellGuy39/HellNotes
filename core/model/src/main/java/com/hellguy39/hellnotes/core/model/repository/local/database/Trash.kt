package com.hellguy39.hellnotes.core.model.repository.local.database

data class Trash(
    val id: Long? = null,
    val note: Note,
    val dateOfAdding: Long
)
