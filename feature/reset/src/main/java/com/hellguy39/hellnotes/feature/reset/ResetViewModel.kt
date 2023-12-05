package com.hellguy39.hellnotes.feature.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.ResetAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetViewModel
@Inject
constructor(
    private val resetAppUseCase: ResetAppUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ResetUiState())
    val uiState = _uiState.asStateFlow()

    fun send(uiEvent: ResetUiEvent) {
        when(uiEvent) {
            ResetUiEvent.Reset -> { reset() }
            ResetUiEvent.ToggleIsResetDatabase -> {
                _uiState.update { state ->
                    state.copy(isResetDatabase = state.isResetDatabase.not())
                }
            }
            ResetUiEvent.ToggleIsResetSettings -> {
                _uiState.update { state ->
                    state.copy(isResetSettings = state.isResetSettings.not())
                }
            }
        }
    }

    private fun reset() {
        viewModelScope.launch {
            val isResetDatabase = _uiState.value.isResetDatabase
            val isResetSettings = _uiState.value.isResetSettings
            resetAppUseCase.invoke(isResetDatabase, isResetSettings)
        }
    }
}

data class ResetUiState(
    val isResetDatabase: Boolean = false,
    val isResetSettings: Boolean = false
) {
    fun resetButtonEnabled() = isResetDatabase || isResetSettings
}

sealed class ResetUiEvent {

    data object Reset : ResetUiEvent()

    data object ToggleIsResetDatabase : ResetUiEvent()

    data object ToggleIsResetSettings : ResetUiEvent()

}