package com.hellguy39.hellnotes.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    object Empty : UiText()

    data class DynamicString(
        val value: String,
    ) : UiText()

    data class StringResources(
        @StringRes val id: Int,
        val args: List<Any> = listOf(),
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResources -> context.getString(id, *args.toTypedArray())
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value
            is StringResources -> stringResource(id, *args.toTypedArray())
        }
    }
}
