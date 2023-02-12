package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.util.LockScreenType

data class AppSettings(

    /* Security */
    val appLockType: LockScreenType,
    val appCode: String,
    val isUseBiometricData: Boolean,

    val isOnBoardingCompleted: Boolean
) {
    constructor(): this(
        appLockType = LockScreenType.None,
        appCode = "",
        isUseBiometricData = false,
        isOnBoardingCompleted = false
    )
}
