package com.hellguy39.hellnotes.core.model

sealed class Language(val code: String) {

    object SystemDefault: Language(code = SYSTEM_DEFAULT)
    object Russian: Language(code = RUSSIAN)
    object English: Language(code = ENGLISH)
    object German: Language(code = GERMAN)
    object French: Language(code = FRENCH)

    companion object {
        private const val SYSTEM_DEFAULT = ""
        private const val RUSSIAN = "ru"
        private const val ENGLISH = "en"
        private const val GERMAN = "de"
        private const val FRENCH = "fr"

        val languageCodes = listOf(SYSTEM_DEFAULT, RUSSIAN, ENGLISH, GERMAN, FRENCH)

        fun from(s: String): Language {
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
