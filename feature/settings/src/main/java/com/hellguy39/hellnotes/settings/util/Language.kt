package com.hellguy39.hellnotes.settings.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.ui.HellNotesStrings

object Language {

    private const val RUSSIAN = "ru"
    private const val ENGLISH = "en"

    val languageCodes = listOf(RUSSIAN, ENGLISH)

    @Composable
    fun getFullName(code: String): String {
        return when(code) {
            RUSSIAN -> stringResource(id = HellNotesStrings.Lan.Russian)
            ENGLISH -> stringResource(id = HellNotesStrings.Lan.English)
            else -> "Unknown"
        }
    }

}