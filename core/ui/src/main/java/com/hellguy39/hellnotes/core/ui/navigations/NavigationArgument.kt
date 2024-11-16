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
package com.hellguy39.hellnotes.core.ui.navigations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.hellguy39.hellnotes.core.common.arguments.Arguments

inline fun <reified T> Arguments<T>.asNavigationArgument(): NamedNavArgument {
    return navArgument(name = key) {
        type = navType()
        defaultValue = emptyValue
    }
}

inline fun <reified T> Arguments<T>.navType(): NavType<*> {
    return when (T::class) {
        String::class -> {
            NavType.StringType
        }
        Int::class -> {
            NavType.IntType
        }
        Long::class -> {
            NavType.LongType
        }
        Float::class -> {
            NavType.FloatType
        }
        else -> throw RuntimeException("Type could not be recognized")
    }
}
