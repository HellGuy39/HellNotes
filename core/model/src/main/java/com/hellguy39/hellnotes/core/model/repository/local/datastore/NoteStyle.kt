package com.hellguy39.hellnotes.core.model.repository.local.datastore

sealed class NoteStyle(val tag: String) {

    data object Outlined: NoteStyle(OUTLINED)

    data object Elevated: NoteStyle(ELEVATED)

    companion object {

        const val OUTLINED = "outlined"
        const val ELEVATED = "elevated"

        fun styles() = listOf(Outlined, Elevated)

        fun fromTag(
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