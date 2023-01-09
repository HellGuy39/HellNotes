package com.hellguy39.hellnotes.model

data class AppSettings(
    /* Security */
    val isPinSetup: Boolean,
    val appPin: String,
    val isUseBiometric: Boolean,
) {
    constructor(): this(false, "", false)
}
