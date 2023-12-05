package com.hellguy39.hellnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.Language
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteStyle
import com.hellguy39.hellnotes.core.model.repository.local.datastore.NoteSwipesState
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val dataStoreRepository: DataStoreRepository,
    val biometricAuth: BiometricAuthenticator,
    private val languageHolder: LanguageHolder
): ViewModel() {

    private val isBiometricAuthAvailable =
        biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success

    private val language = MutableStateFlow(languageHolder.getLanguage())

    val uiState = combine(
        dataStoreRepository.readSecurityState(),
        dataStoreRepository.readNoteStyleState(),
        dataStoreRepository.readNoteSwipesState(),
        dataStoreRepository.readLastBackupDate(),
        language
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
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState()
        )


    fun send(uiEvent: SettingsUiEvent) {
        when(uiEvent) {
            is SettingsUiEvent.ToggleIsUseBiometricData -> {
               saveIsUseBiometricData(uiEvent.isUseBiometric)
            }
            is SettingsUiEvent.FetchLanguage -> {
                language.update { languageHolder.getLanguage() }
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
    data object FetchLanguage: SettingsUiEvent()
}

data class SettingsUiState(
    val securityState: SecurityState = SecurityState.initialInstance(),
    val language: Language = Language.SystemDefault,
    val lastBackupDate: Long = 0L,
    val isBioAuthAvailable: Boolean = false,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
    val noteSwipesState: NoteSwipesState = NoteSwipesState.initialInstance(),
)