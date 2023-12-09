package com.hellguy39.hellnotes.feature.languageselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.tools.LanguageHolder
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
