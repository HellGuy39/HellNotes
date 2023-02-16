package com.hellguy39.hellnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.AppSettings
import com.hellguy39.hellnotes.core.model.util.Language
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
                dataStoreRepository.readAppSettings().collect { settings ->
                    settingsViewModelState.update { state ->
                        state.copy(appSettings = settings)
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
            dataStoreRepository.saveIsUseBiometricData(isUseBiometric)
        }
    }

    private fun isBiometricAuthAvailable(): Boolean {
        return biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success
    }

}

private data class SettingsViewModelState(
    val appSettings: AppSettings = AppSettings(),
    val lanCode: String = Language.SystemDefault.code,
    val isBioAuthAvailable: Boolean = false
) {
    fun toUiState() = SettingsUiState(
        appSettings = appSettings,
        isBioAuthAvailable = isBioAuthAvailable,
        lanCode = lanCode
    )
}
data class SettingsUiState(
    val appSettings: AppSettings,
    val lanCode: String,
    val isBioAuthAvailable: Boolean
)