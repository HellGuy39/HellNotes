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
package com.hellguy39.hellnotes.tools

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.hellguy39.hellnotes.core.domain.tools.LanguageHolder
import com.hellguy39.hellnotes.core.model.Language
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@SuppressLint("NewApi")
class LanguageHolderImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : LanguageHolder {
        private val localeManager by lazy {
            context.getSystemService(LocaleManager::class.java)
        }

        private val _languageFlow = MutableStateFlow(getLanguage())
        override val languageFlow: Flow<Language> = _languageFlow

        override suspend fun setLanguage(language: Language) {
            _languageFlow.emit(language)
            setLanguageByTag(language.tag)
        }

        override fun getLanguage(): Language {
            return Language.fromTag(getLanguageTag())
        }

        private fun setLanguageByTag(tag: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                localeManager.applicationLocales = LocaleList.forLanguageTags(tag)
            } else {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(tag),
                )
            }
        }

        private fun getLanguageTag(): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val appLocales = localeManager.applicationLocales
                appLocales.toLanguageTags()
            } else {
                val appLocales = AppCompatDelegate.getApplicationLocales()
                appLocales.toLanguageTags()
            }
        }
    }
