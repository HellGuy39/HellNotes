package com.hellguy39.hellnotes.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val biometricAuth: BiometricAuthenticator,
    val languageHolder: LanguageHolder
): ViewModel() {

    private val _appSettings: MutableStateFlow<AppSettings> = MutableStateFlow(AppSettings())
    val appSettings = _appSettings.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreRepository.readAppSettings().collect { settings ->
                _appSettings.update { settings }
            }
        }
    }

    fun setPin(pin: String) = viewModelScope.launch {
        _appSettings.update {
            it.copy(
                isAppLocked = true,
                appPin = pin
            )
        }
        saveSettings()
    }

    fun deletePin() = viewModelScope.launch {
        _appSettings.update {
            it.copy(
                isAppLocked = false,
                appPin = ""
            )
        }
        saveSettings()
    }

    fun setIsUseBiometric(isUseBio: Boolean) = viewModelScope.launch {
        _appSettings.update { it.copy(isBiometricSetup = isUseBio) }
        saveSettings()
    }

    fun isBiometricAuthAvailable(): Boolean {
        return when(biometricAuth.deviceBiometricSupportStatus()) {
            DeviceBiometricStatus.Success -> true
            else -> false
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            _appSettings.value.let { settings ->
                dataStoreRepository.saveAppSettings(settings)
            }
        }
    }

}