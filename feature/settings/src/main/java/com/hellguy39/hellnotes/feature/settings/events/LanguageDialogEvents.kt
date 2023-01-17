package com.hellguy39.hellnotes.feature.settings.events

interface LanguageDialogEvents {
    fun show()
    fun dismiss()
    fun onLanguageSelected(languageCode: String)
    fun getCurrentLanCode(): String
}