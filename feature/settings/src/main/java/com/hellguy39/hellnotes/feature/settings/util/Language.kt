package com.hellguy39.hellnotes.feature.settings.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

object Language {

    private const val SYSTEM_DEFAULT = ""
    private const val RUSSIAN = "ru-RU"
    private const val ENGLISH = "en-US"
    private const val GERMAN = "de"
    private const val FRENCH = "fr"

    val languageCodes = listOf(SYSTEM_DEFAULT, RUSSIAN, ENGLISH, GERMAN, FRENCH)

    @Composable
    fun getFullName(code: String): String {
        return when(code) {
            RUSSIAN -> stringResource(id = HellNotesStrings.Lan.Russian)
            ENGLISH -> stringResource(id = HellNotesStrings.Lan.English)
            GERMAN -> "German (Planned)"
            FRENCH -> "French (Planned)"
            SYSTEM_DEFAULT -> "System default"
            else -> "Unknown"
        }
    }

}