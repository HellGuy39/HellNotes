package com.hellguy39.hellnotes.feature.language_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.util.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(
    private val languageHolder: LanguageHolder
): ViewModel() {

    private val _uiState = MutableStateFlow(
        LanguageSelectionUiState(languageCode = Language.languageCodes[0])
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(languageCode = getCurrentLanguageCode()) }
        }
    }

    fun setLanguageCode(code: String) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(languageCode = code) }
            languageHolder.setLanguageCode(code)
        }
    }

    private fun getCurrentLanguageCode() = languageHolder.getLanguageCode()

}

data class LanguageSelectionUiState(
    val languageCode: String
)