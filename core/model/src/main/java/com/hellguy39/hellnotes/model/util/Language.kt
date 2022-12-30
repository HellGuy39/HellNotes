package com.hellguy39.hellnotes.model.util

//sealed interface Language {
//    object Russian: Language
//    object English: Language
//
//    fun parse(): String {
//        return when(this) {
//            is Russian -> RUSSIAN
//            is English -> ENGLISH
//        }
//    }
//
//    companion object {
//
//        val languageCodes = listOf("ru", "en")
//
//        fun from(s: String): Language {
//            return when(s) {
//                RUSSIAN -> Russian
//                ENGLISH -> English
//                else -> English
//            }
//        }
//        const val RUSSIAN = "ru"
//        const val ENGLISH = "en"
//    }
//}