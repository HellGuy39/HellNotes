package com.hellguy39.hellnotes.settings.events

interface PINDialogEvents {
    fun show()
    fun dismiss()
    fun onNewPin(pin: String)
}