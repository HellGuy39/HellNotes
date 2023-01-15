package com.hellguy39.hellnotes.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.domain.android_system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.domain.android_system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.domain.repository.AppSettingsRepository
import com.hellguy39.hellnotes.model.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository,
    private val biometricAuth: BiometricAuthenticator
): ViewModel() {

    private val _appSettings: MutableStateFlow<AppSettings> = MutableStateFlow(AppSettings())
    val appSettings = _appSettings.asStateFlow()

    init {
        fetchSettings()
    }

    private fun fetchSettings() = viewModelScope.launch {
        val settings = appSettingsRepository.getAppSettings()
        _appSettings.update { settings }
    }

    fun setPin(pin: String) = viewModelScope.launch {
        _appSettings.update {
            it.copy(
                isPinSetup = true,
                appPin = pin
            )
        }
        saveSettings()
    }

    fun deletePin() = viewModelScope.launch {
        _appSettings.update {
            it.copy(
                isPinSetup = false,
                appPin = ""
            )
        }
        saveSettings()
    }

    fun setIsUseBiometric(isUseBio: Boolean) = viewModelScope.launch {
        _appSettings.update { it.copy(isUseBiometric = isUseBio) }
        saveSettings()
    }

    fun isBiometricAuthAvailable(): Boolean {
        return when(biometricAuth.deviceBiometricSupportStatus()) {
            DeviceBiometricStatus.Success -> true
            else -> false
        }
    }

    private fun saveSettings() = viewModelScope.launch {
        _appSettings.value.let {
            appSettingsRepository.saveAppSettings(it)
        }
    }

}