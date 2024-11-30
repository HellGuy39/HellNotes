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
package com.hellguy39.hellnotes.feature.settings.screen.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.system.LanguageHolder
import com.hellguy39.hellnotes.core.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSelectionViewModel
    @Inject
    constructor(
        private val languageHolder: LanguageHolder,
    ) : ViewModel() {
        val uiState =
            languageHolder.languageFlow
                .map { language ->
                    LanguageSelectionUiState(
                        language = language,
                    )
                }
                .stateIn(
                    initialValue = LanguageSelectionUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        fun setLanguage(language: Language) {
            viewModelScope.launch {
                languageHolder.setLanguage(language)
            }
        }
    }

data class LanguageSelectionUiState(
    val language: Language = Language.SystemDefault,
)
