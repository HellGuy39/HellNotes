package com.hellguy39.hellnotes.core.model

sealed class LockScreenType(val tag: String) {

    data object None: LockScreenType(NONE)
    data object Pin: LockScreenType(PIN)
    data object Password: LockScreenType(PASSWORD)
    data object Slide: LockScreenType(SLIDE)
    data object Pattern: LockScreenType(PATTERN)

    companion object {

        private const val NONE = "none"
        private const val PIN = "pin"
        private const val PASSWORD = "password"
        private const val SLIDE = "slide"
        private const val PATTERN = "pattern"

        fun default() = None

        fun fromTag(
            tag: String?,
            defaultValue: LockScreenType = default()
        ): LockScreenType {
            return when(tag) {
                NONE -> None
                PIN -> Pin
                PASSWORD -> Password
                SLIDE -> Slide
                PATTERN -> Pattern
                else -> defaultValue
            }
        }
    }
}