package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.wrapper.Tagged
import com.hellguy39.hellnotes.core.model.wrapper.TaggedCompanion
import kotlinx.coroutines.flow.flow

sealed class Theme(tag: String) : Tagged(tag, key) {

    data object Dark : Theme(tag = DARK)

    data object Light : Theme(tag = LIGHT)

    data object System : Theme(tag = SYSTEM)

    companion object : TaggedCompanion<Theme> {

        override val key = "theme"

        override fun defaultValue() = System

        override fun fromTag(tag: String?) = when (tag) {
            DARK -> Dark
            LIGHT -> Light
            SYSTEM -> System
            else -> defaultValue()
        }

        override fun values() = listOf(System, Dark, Light)

        override fun valuesFlow() = flow { emit(values()) }

        private const val DARK = "dark"
        private const val LIGHT = "light"
        private const val SYSTEM = "system"
    }
}
