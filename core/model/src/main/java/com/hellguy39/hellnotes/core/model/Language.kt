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

sealed class Language(val tag: String) {
    data object SystemDefault : Language(tag = SYSTEM_DEFAULT)

    data object Russian : Language(tag = RUSSIAN)

    data object English : Language(tag = ENGLISH)

    data object German : Language(tag = GERMAN)

    data object French : Language(tag = FRENCH)

    companion object {
        private const val SYSTEM_DEFAULT = ""
        private const val RUSSIAN = "ru"
        private const val ENGLISH = "en"
        private const val GERMAN = "de"
        private const val FRENCH = "fr"

        fun languages() = listOf(SystemDefault, Russian, English, German, French)

        fun fromTag(s: String): Language {
            return when (s) {
                SYSTEM_DEFAULT -> SystemDefault
                RUSSIAN -> Russian
                ENGLISH -> English
                GERMAN -> German
                FRENCH -> French
                else -> SystemDefault
            }
        }
    }
}
