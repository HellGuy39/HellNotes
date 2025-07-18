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

    data object ChineseSimplified : Language(tag = CHINESE_SIMPLIFIED)

    data object Arabic : Language(tag = ARABIC)

    data object SpanishLatinAmerica : Language(tag = SPANISH_LATIN_AMERICA)

    data object Czech : Language(tag = CZECH)

    data object SpanishSpain : Language(tag = SPANISH_SPAIN)

    data object Hindi : Language(tag = HINDI)

    data object Indonesian : Language(tag = INDONESIAN)

    data object Japanese : Language(tag = JAPANESE)

    data object Korean : Language(tag = KOREAN)

    data object PortugueseBrazil : Language(tag = PORTUGUESE_BRAZIL)

    data object Ukrainian : Language(tag = UKRAINIAN)

    companion object {
        private const val SYSTEM_DEFAULT = ""
        private const val RUSSIAN = "ru"
        private const val ENGLISH = "en"
        private const val GERMAN = "de"
        private const val FRENCH = "fr"
        private const val CHINESE_SIMPLIFIED = "zh-CN"
        private const val ARABIC = "ar"
        private const val SPANISH_LATIN_AMERICA = "b+es+419"
        private const val CZECH = "cs"
        private const val SPANISH_SPAIN = "es-ES"
        private const val HINDI = "hi"
        private const val INDONESIAN = "in"
        private const val JAPANESE = "ja"
        private const val KOREAN = "ko"
        private const val PORTUGUESE_BRAZIL = "pt-BR"
        private const val UKRAINIAN = "uk"

        fun languages() = listOf(
            SystemDefault,
            Russian,
            English,
            German,
            French,
            ChineseSimplified,
            Arabic,
            SpanishLatinAmerica,
            Czech,
            SpanishSpain,
            Hindi,
            Indonesian,
            Japanese,
            Korean,
            PortugueseBrazil,
            Ukrainian
        )

        fun fromTag(s: String): Language {
            return when (s) {
                SYSTEM_DEFAULT -> SystemDefault
                RUSSIAN -> Russian
                ENGLISH -> English
                GERMAN -> German
                FRENCH -> French
                CHINESE_SIMPLIFIED -> ChineseSimplified
                ARABIC -> Arabic
                SPANISH_LATIN_AMERICA -> SpanishLatinAmerica
                CZECH -> Czech
                SPANISH_SPAIN -> SpanishSpain
                HINDI -> Hindi
                INDONESIAN -> Indonesian
                JAPANESE -> Japanese
                KOREAN -> Korean
                PORTUGUESE_BRAZIL -> PortugueseBrazil
                UKRAINIAN -> Ukrainian
                else -> SystemDefault
            }
        }
    }
}
