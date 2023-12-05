package com.hellguy39.hellnotes.core.model

sealed class Language(val tag: String) {

    data object SystemDefault: Language(tag = SYSTEM_DEFAULT)

    data object Russian: Language(tag = RUSSIAN)

    data object English: Language(tag = ENGLISH)

    data object German: Language(tag = GERMAN)

    data object French: Language(tag = FRENCH)

    companion object {

        private const val SYSTEM_DEFAULT = ""
        private const val RUSSIAN = "ru"
        private const val ENGLISH = "en"
        private const val GERMAN = "de"
        private const val FRENCH = "fr"

        fun languages() = listOf(SystemDefault, Russian, English, German, French)

        fun fromTag(s: String): Language {
            return when(s) {
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
