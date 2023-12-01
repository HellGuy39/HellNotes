package com.hellguy39.hellnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val dataStoreRepository: DataStoreRepository,
    val biometricAuth: BiometricAuthenticator,
    languageHolder: LanguageHolder
): ViewModel() {

    private val isBiometricAuthAvailable =
        biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success

    val uiState = combine(
        dataStoreRepository.readSecurityState(),
        dataStoreRepository.readNoteStyleState(),
        dataStoreRepository.readNoteSwipesState(),
        dataStoreRepository.readLastBackupDate(),
        languageHolder.languageFlow,
    ) { securityState, noteStyle, noteSwipesState, lastBackupDate, language ->
        SettingsUiState(
            securityState = securityState,
            noteStyle = noteStyle,
            noteSwipesState = noteSwipesState,
            isBioAuthAvailable = isBiometricAuthAvailable,
            language = language,
            lastBackupDate = lastBackupDate
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = SettingsUiState.initialInstance()
        )


    fun send(uiEvent: SettingsUiEvent) {
        when(uiEvent) {
            is SettingsUiEvent.ToggleIsUseBiometricData -> {
               saveIsUseBiometricData(uiEvent.isUseBiometric)
            }
        }
    }

    private fun saveIsUseBiometricData(isUseBiometric: Boolean) {
        viewModelScope.launch {
            val state = uiState.value.securityState
            dataStoreRepository.saveSecurityState(state.copy(isUseBiometricData = isUseBiometric))
        }
    }

}

sealed class SettingsUiEvent {
    data class ToggleIsUseBiometricData(val isUseBiometric: Boolean): SettingsUiEvent()
}

data class SettingsUiState(
    val securityState: SecurityState,
    val language: Language,
    val lastBackupDate: Long,
    val isBioAuthAvailable: Boolean,
    val noteStyle: NoteStyle,
    val noteSwipesState: NoteSwipesState
) {
    companion object {
        fun initialInstance() = SettingsUiState(
            securityState = SecurityState.initialInstance(),
            language = Language.SystemDefault,
            isBioAuthAvailable = false,
            noteStyle = NoteStyle.Outlined,
            noteSwipesState = NoteSwipesState.initialInstance(),
            lastBackupDate = 0L
        )
    }
}