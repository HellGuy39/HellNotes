package com.hellguy39.hellnotes.core.model.local.database

data class Label(
    val id: Long? = null,
    val name: String = "",
    val noteIds: List<Long> = listOf()
)
