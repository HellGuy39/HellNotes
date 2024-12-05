package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle

data class AppearanceState(
    val theme: Theme,
    val colorMode: ColorMode,
    val noteStyle: NoteStyle
)
