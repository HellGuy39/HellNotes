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
package com.hellguy39.hellnotes.core.model

sealed class LockScreenType(val tag: String) {
    data object None : LockScreenType(NONE)

    data object Pin : LockScreenType(PIN)

    data object Password : LockScreenType(PASSWORD)

    data object Slide : LockScreenType(SLIDE)

    data object Pattern : LockScreenType(PATTERN)

    companion object {
        private const val NONE = "none"
        private const val PIN = "pin"
        private const val PASSWORD = "password"
        private const val SLIDE = "slide"
        private const val PATTERN = "pattern"

        fun default() = None

        fun fromTag(
            tag: String?,
            defaultValue: LockScreenType = default(),
        ): LockScreenType {
            return when (tag) {
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
