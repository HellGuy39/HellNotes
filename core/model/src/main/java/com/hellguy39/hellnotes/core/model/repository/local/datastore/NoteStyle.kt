package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed interface NoteStyle {
    object Outlined: NoteStyle
    object Elevated: NoteStyle

    fun string(): String {
        return when(this) {
            is Outlined -> OUTLINED
            is Elevated -> ELEVATED
        }
    }

    companion object {

        const val OUTLINED = "outlined"
        const val ELEVATED = "elevated"

        fun from(
            s: String?,
            defaultValue: NoteStyle = Outlined
        ): NoteStyle {
            return when(s) {
                OUTLINED -> Outlined
                ELEVATED -> Elevated
                else -> defaultValue
            }
        }
    }
}