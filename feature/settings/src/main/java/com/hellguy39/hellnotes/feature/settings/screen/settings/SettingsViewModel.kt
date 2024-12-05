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
package com.hellguy39.hellnotes.feature.settings.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.settings.SettingsRepository
import com.hellguy39.hellnotes.core.domain.repository.system.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.repository.system.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.repository.system.LanguageHolder
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val settingsRepository: SettingsRepository,
        val biometricAuth: BiometricAuthenticator,
        languageHolder: LanguageHolder,
    ) : ViewModel() {
        private val isBiometricAuthAvailable =
            biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success

        val uiState =
            combine(
                settingsRepository.readSecurityState(),
                settingsRepository.getAppearanceStateFlow(),
                settingsRepository.readNoteSwipesState(),
                settingsRepository.readLastBackupDate(),
                languageHolder.languageFlow,
            ) { securityState, appearanceState, noteSwipesState, lastBackupDate, language ->
                SettingsUiState(
                    securityState = securityState,
                    noteStyle = appearanceState.noteStyle,
                    noteSwipesState = noteSwipesState,
                    isBioAuthAvailable = isBiometricAuthAvailable,
                    language = language,
                    lastBackupDate = lastBackupDate,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = SettingsUiState(),
                )

        fun send(uiEvent: SettingsUiEvent) {
            when (uiEvent) {
                is SettingsUiEvent.ToggleIsUseBiometricData -> {
                    saveIsUseBiometricData(uiEvent.isUseBiometric)
                }
            }
        }

        private fun saveIsUseBiometricData(isUseBiometric: Boolean) {
            viewModelScope.launch {
                val state = uiState.value.securityState
                settingsRepository.saveSecurityState(state.copy(isUseBiometricData = isUseBiometric))
            }
        }
    }

sealed class SettingsUiEvent {
    data class ToggleIsUseBiometricData(val isUseBiometric: Boolean) : SettingsUiEvent()
}

data class SettingsUiState(
    val securityState: SecurityState = SecurityState.initialInstance(),
    val language: Language = Language.SystemDefault,
    val lastBackupDate: Long = 0L,
    val isBioAuthAvailable: Boolean = false,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
    val noteSwipesState: NoteSwipesState = NoteSwipesState(),
)
