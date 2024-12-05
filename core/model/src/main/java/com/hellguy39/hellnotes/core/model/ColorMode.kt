package com.hellguy39.hellnotes.core.model

import com.hellguy39.hellnotes.core.model.wrapper.Tagged
import com.hellguy39.hellnotes.core.model.wrapper.TaggedCompanion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class ColorMode(tag: String) : Tagged(tag, key) {

    data object Default : ColorMode(tag = DEFAULT)

    data object MaterialYou : ColorMode(tag = MATERIAL_YOU)

    data object AmoledBlack : ColorMode(tag = AMOLED_BLACK)

    companion object : TaggedCompanion<ColorMode> {

        override val key = "color_mode"

        override fun defaultValue(): ColorMode = Default

        override fun fromTag(tag: String?) = when (tag) {
            DEFAULT -> Default
            MATERIAL_YOU -> MaterialYou
            AMOLED_BLACK -> AmoledBlack
            else -> defaultValue()
        }

        override fun values() = listOf(Default, MaterialYou, AmoledBlack)

        override fun valuesFlow(): Flow<List<ColorMode>> = flow { emit(values()) }

        private const val AMOLED_BLACK = "amoled_black"
        private const val DEFAULT = "default"
        private const val MATERIAL_YOU = "material_you"
    }
}
