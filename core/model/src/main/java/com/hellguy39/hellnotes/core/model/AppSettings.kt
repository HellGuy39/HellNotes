package com.hellguy39.hellnotes.core.model

data class AppSettings(
    /* Security */
    val isAppLocked: Boolean,
    val appPin: String,
    val isBiometricSetup: Boolean,
    val isOnBoardingCompleted: Boolean
) {
    constructor(): this(false, "", false, false)
}
