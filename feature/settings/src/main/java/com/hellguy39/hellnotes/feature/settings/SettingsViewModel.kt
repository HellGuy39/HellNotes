package com.hellguy39.hellnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.NoteSwipesState
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.Language
import com.hellguy39.hellnotes.core.model.util.NoteStyle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val biometricAuth: BiometricAuthenticator,
    private val languageHolder: LanguageHolder
): ViewModel() {

    private val settingsViewModelState = MutableStateFlow(SettingsViewModelState())

    val uiState = settingsViewModelState
        .map(SettingsViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = settingsViewModelState.value.toUiState()
        )

    init {
        updateLanguageCode()
        viewModelScope.launch {
            launch {
                settingsViewModelState.update { state ->
                    state.copy(isBioAuthAvailable = isBiometricAuthAvailable())
                }
            }
            launch {
                dataStoreRepository.readSecurityState().collect { settings ->
                    settingsViewModelState.update { state ->
                        state.copy(securityState = settings)
                    }
                }
            }
            launch {
                dataStoreRepository.readNoteStyleState().collect { noteStyle ->
                    settingsViewModelState.update { state ->
                        state.copy(noteStyle = noteStyle)
                    }
                }
            }
            launch {
                dataStoreRepository.readNoteSwipesState().collect { noteSwipeState ->
                    settingsViewModelState.update { state ->
                        state.copy(noteSwipesState = noteSwipeState)
                    }
                }
            }
        }
    }

    fun updateLanguageCode() {
        viewModelScope.launch {
            settingsViewModelState.update { state ->
                state.copy(
                    lanCode = languageHolder.getLanguageCode()
                )
            }
        }
    }

    fun saveIsUseBiometricData(isUseBiometric: Boolean) {
        viewModelScope.launch {
            val state = settingsViewModelState.value.securityState
            dataStoreRepository.saveSecurityState(state.copy(isUseBiometricData = isUseBiometric))
        }
    }

    private fun isBiometricAuthAvailable(): Boolean {
        return biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success
    }

}

private data class SettingsViewModelState(
    val securityState: SecurityState = SecurityState.initialInstance(),
    val lanCode: String = Language.SystemDefault.code,
    val isBioAuthAvailable: Boolean = false,
    val noteStyle: NoteStyle = NoteStyle.Outlined,
    val noteSwipesState: NoteSwipesState = NoteSwipesState.initialInstance()
) {
    fun toUiState() = SettingsUiState(
        securityState = securityState,
        isBioAuthAvailable = isBioAuthAvailable,
        lanCode = lanCode,
        noteStyle = noteStyle,
        noteSwipesState = noteSwipesState
    )
}
data class SettingsUiState(
    val securityState: SecurityState,
    val lanCode: String,
    val isBioAuthAvailable: Boolean,
    val noteStyle: NoteStyle,
    val noteSwipesState: NoteSwipesState
)