package com.hellguy39.hellnotes.settings.events

interface LanguageDialogEvents {
    fun show()
    fun dismiss()
    fun onLanguageSelected(languageCode: String)
    fun getCurrentLanCode(): String
}