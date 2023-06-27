package com.hellguy39.hellnotes.core.model.local.datastore

sealed class ThemeState {

    object Light: ThemeState()

    object Dark: ThemeState()

    object System: ThemeState()

    override fun toString() = when(this) {
        is Light -> LIGHT
        is Dark -> DARK
        is System -> SYSTEM
    }

    companion object {

        const val SYSTEM = "system"
        const val LIGHT = "light"
        const val DARK = "dark"

        fun from(
            s: String?,
            defaultValue: ThemeState = System
        ) = when(s) {
            DARK -> Dark
            LIGHT -> Light
            SYSTEM -> System
            else -> defaultValue
        }
    }
}
