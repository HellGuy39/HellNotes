package com.hellguy39.hellnotes.core.common.arguments

import android.content.Intent
import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getArgument(argument: Arguments<T>): T {
    return get<T>(argument.key) ?: argument.emptyValue
}

inline fun <reified T> Intent.getArgument(argument: Arguments<T>): T {
    return when (T::class) {
        String::class -> {
            extras?.getString(argument.key) ?: argument.emptyValue
        }
        Int::class -> {
            extras?.getInt(argument.key) ?: argument.emptyValue
        }
        Long::class -> {
            extras?.getLong(argument.key) ?: argument.emptyValue
        }
        Double::class -> {
            extras?.getDouble(argument.key) ?: argument.emptyValue
        }
        Short::class -> {
            extras?.getShort(argument.key) ?: argument.emptyValue
        }
        Float::class -> {
            extras?.getFloat(argument.key) ?: argument.emptyValue
        }
        else -> throw RuntimeException("Type could not be recognized")
    } as T
}
