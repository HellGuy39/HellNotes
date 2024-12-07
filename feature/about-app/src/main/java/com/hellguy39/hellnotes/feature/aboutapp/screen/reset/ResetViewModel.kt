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
package com.hellguy39.hellnotes.feature.aboutapp.screen.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.usecase.ResetAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ResetViewModel
    @Inject
    constructor(
        private val resetAppUseCase: ResetAppUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ResetUiState())
        val uiState = _uiState.asStateFlow()

        fun send(uiEvent: ResetUiEvent) {
            when (uiEvent) {
                ResetUiEvent.Reset -> {
                    reset()
                }
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
    val isResetSettings: Boolean = false,
) {
    fun resetButtonEnabled() = isResetDatabase || isResetSettings
}

sealed class ResetUiEvent {
    data object Reset : ResetUiEvent()

    data object ToggleIsResetDatabase : ResetUiEvent()

    data object ToggleIsResetSettings : ResetUiEvent()
}
