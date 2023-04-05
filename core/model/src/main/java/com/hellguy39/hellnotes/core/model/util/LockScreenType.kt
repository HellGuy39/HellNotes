package com.hellguy39.hellnotes.core.model.util

sealed class LockScreenType {
    object None: LockScreenType()
    object Pin: LockScreenType()
    object Password: LockScreenType()
    object Slide: LockScreenType()
    object Pattern: LockScreenType()

    fun string(): String {
        return when(this) {
            None -> NONE
            Pin -> PIN
            Password -> PASSWORD
            Slide -> SLIDE
            Pattern -> PATTERN
        }
    }

    companion object {

        private const val NONE = "none"
        private const val PIN = "pin"
        private const val PASSWORD = "password"
        private const val SLIDE = "slide"
        private const val PATTERN = "pattern"

        fun from(s: String?): LockScreenType {
            return when(s) {
                NONE -> None
                PIN -> Pin
                PASSWORD -> Password
                SLIDE -> Slide
                PATTERN -> Pattern
                else -> None
            }
        }

    }

}