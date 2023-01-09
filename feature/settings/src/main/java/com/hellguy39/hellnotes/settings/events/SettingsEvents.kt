package com.hellguy39.hellnotes.settings.events

interface SettingsEvents {
    fun setupPIN()
    fun updatePIN()
    fun deletePIN()
    fun setUseBiometric(isUseBio: Boolean)
}