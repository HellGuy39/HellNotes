package com.hellguy39.hellnotes.feature.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.ui.components.NumberKeyboardKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    val biometricAuth: BiometricAuthenticator
): ViewModel() {

    private var _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val lockViewModelState = MutableStateFlow(LockViewModelState())

    val uiState = lockViewModelState
        .map(LockViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            lockViewModelState.value.toUiState()
        )

    init {
        viewModelScope.launch {
            launch {
                dataStoreRepository.readAppSettings().collect { settings ->
                    lockViewModelState.update {
                        it.copy(
                            appPin = settings.appPin,
                            isBiometricsAllowed = settings.isBiometricSetup
                        )
                    }
                }
            }
            launch {
                biometricAuth.setOnAuthListener { result ->
                    when (result) {
                        is AuthenticationResult.Success -> {
                            lockViewModelState.update { state ->
                                state.copy(lockState = LockState.Unlocked)
                            }
                        }
                        is AuthenticationResult.Failed -> Unit
                        is AuthenticationResult.Error -> Unit
                    }
                }
            }
        }
    }

    fun enterKey(key: String) {
        viewModelScope.launch {
            when (key) {
                NumberKeyboardKeys.KeyBackspace -> {
                    lockViewModelState.update {
                        it.copy(
                            inputPin = it.inputPin.dropLast(1)
                        )
                    }
                }
                else -> {
                    lockViewModelState.update {
                        it.copy(
                            inputPin = it.inputPin.plus(key)
                        )
                    }
                }
            }
            checkPin()
        }
    }

    private fun checkPin() {
        lockViewModelState.value.let { state ->
            if (state.inputPin.length >= 4) {
                if (state.inputPin == state.appPin) {
                    lockViewModelState.update {
                        state.copy(lockState = LockState.Unlocked)
                    }
                } else {
                    lockViewModelState.update {
                        state.copy(lockState = LockState.WrongPin)
                    }
                    clearPin()
                }
            } else {
                lockViewModelState.update {
                    state.copy(lockState = LockState.Locked)
                }
            }
        }
    }

    fun clearPin() {
        viewModelScope.launch {
            lockViewModelState.update { it.copy(inputPin = "") }
        }
    }

    fun authByBiometric(onSuccess: () -> Unit) {
        when (biometricAuth.deviceBiometricSupportStatus()) {
            DeviceBiometricStatus.Success -> {
                onSuccess()
            }
            DeviceBiometricStatus.NoHardware -> {
                _errorMessage.update { "No hardware" }
            }
            DeviceBiometricStatus.Unsupported -> {
                _errorMessage.update { "Unsupported" }
            }
            DeviceBiometricStatus.HardwareUnavailable -> {
                _errorMessage.update { "Hardware unavailable" }
            }
            DeviceBiometricStatus.NoneEnrolled -> {
                _errorMessage.update { "Biometric none enrolled" }
            }
            DeviceBiometricStatus.SecurityUpdateRequired -> {
                _errorMessage.update { "Security update required" }
            }
            DeviceBiometricStatus.StatusUnknown -> {
                _errorMessage.update { "Status unknown" }
            }
        }
    }

}

private data class LockViewModelState(
    val appPin: String = "",
    val inputPin: String = "",
    val lockState: LockState = LockState.Locked,
    val isBiometricsAllowed: Boolean = false
) {
    fun toUiState() = LockUiState(
        pin = inputPin,
        lockState = lockState,
        isBiometricsAllowed = isBiometricsAllowed
    )
}

data class LockUiState(
    val pin: String,
    val lockState: LockState,
    val isBiometricsAllowed: Boolean
)

sealed interface LockState {
    object WrongPin : LockState
    object Unlocked : LockState
    object Locked : LockState
}