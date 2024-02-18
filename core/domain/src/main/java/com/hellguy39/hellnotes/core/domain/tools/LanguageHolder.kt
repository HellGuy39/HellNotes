package com.hellguy39.hellnotes.core.domain.tools

import com.hellguy39.hellnotes.core.model.Language
import kotlinx.coroutines.flow.Flow

interface LanguageHolder {
    val languageFlow: Flow<Language>

    suspend fun setLanguage(language: Language)

    fun getLanguage(): Language
}
