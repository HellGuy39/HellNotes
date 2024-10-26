/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
