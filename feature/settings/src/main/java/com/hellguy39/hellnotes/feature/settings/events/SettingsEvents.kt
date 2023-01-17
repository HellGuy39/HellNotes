package com.hellguy39.hellnotes.feature.settings.events

interface SettingsEvents {
    fun setupPIN()
    fun updatePIN()
    fun deletePIN()
    fun setUseBiometric(isUseBio: Boolean)
}