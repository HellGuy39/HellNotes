package com.hellguy39.hellnotes.feature.lock

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    val biometricAuth: BiometricAuthenticator,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val errorMessage = MutableStateFlow("")
    private val lockState: MutableStateFlow<LockState> = MutableStateFlow(LockState.Locked)
    private val password = MutableStateFlow("")

    val uiState: StateFlow<LockUiState> =
        combine(
            dataStoreRepository.readSecurityState(),
            password,
            lockState,
            errorMessage
        ) { securityState, password, lockState, errorMessage ->
            LockUiState(
                securityState = securityState,
                password = password,
                lockState = lockState,
                errorMessage = errorMessage
            )
        }
            .stateIn(
                initialValue = LockUiState.initialInstance(),
                started = SharingStarted.WhileSubscribed(5_000),
                scope = viewModelScope
            )

    init {
        viewModelScope.launch {
            launch {
                biometricAuth.setOnAuthListener { result ->
                    when (result) {
                        is AuthenticationResult.Success -> {
                            lockState.update { LockState.Unlocked }
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
                    password.update { state -> state.dropLast(1) }
                }
                NumberKeyboardKeys.KeyEnter -> { enterPassword() }
                else -> {
                    password.update { state -> state.plus(key) }
                }
            }

            lockState.update { LockState.Locked }
        }
    }

    fun enterValue(value: String) {
        viewModelScope.launch {
            password.update { value }
        }
    }

    fun enterPassword() {
        viewModelScope.launch {
            if (password.value == uiState.value.securityState.password) {
                lockState.update { LockState.Unlocked }
            } else {
                lockState.update { LockState.WrongPin }
                clearPassword()
            }
        }
    }

    fun clearPassword() {
        viewModelScope.launch {
            password.update { "" }
        }
    }

    fun authByBiometric(onSuccess: () -> Unit) {
        when (biometricAuth.deviceBiometricSupportStatus()) {
            DeviceBiometricStatus.Success -> {
                if (uiState.value.securityState.isUseBiometricData) {
                    onSuccess()
                } else {
                    errorMessage.update { "Biometric data is not allowed" }
                }
            }
            DeviceBiometricStatus.NoHardware -> {
                errorMessage.update { "No hardware" }
            }
            DeviceBiometricStatus.Unsupported -> {
                errorMessage.update { "Unsupported" }
            }
            DeviceBiometricStatus.HardwareUnavailable -> {
                errorMessage.update { "Hardware unavailable" }
            }
            DeviceBiometricStatus.NoneEnrolled -> {
                errorMessage.update { "Biometric none enrolled" }
            }
            DeviceBiometricStatus.SecurityUpdateRequired -> {
                errorMessage.update { "Security update required" }
            }
            DeviceBiometricStatus.StatusUnknown -> {
                errorMessage.update { "Status unknown" }
            }
        }
    }
}

data class LockUiState(
    val securityState: SecurityState,
    val errorMessage: String,
    val password: String,
    val lockState: LockState,
) {
    companion object {
        fun initialInstance() = LockUiState(
            securityState = SecurityState.initialInstance(),
            password = "",
            lockState = LockState.Locked,
            errorMessage = ""
        )
    }
}

sealed interface LockState {
    object WrongPin : LockState
    object Unlocked : LockState
    object Locked : LockState
}