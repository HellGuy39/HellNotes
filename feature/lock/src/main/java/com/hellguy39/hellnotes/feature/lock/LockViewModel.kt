package com.hellguy39.hellnotes.feature.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.tools.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.tools.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.tools.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.model.repository.local.datastore.SecurityState
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockViewModel
    @Inject
    constructor(
        dataStoreRepository: DataStoreRepository,
        val biometricAuth: BiometricAuthenticator,
    ) : ViewModel() {
        private val errorMessage = MutableStateFlow("")
        private val lockState: MutableStateFlow<LockState> = MutableStateFlow(LockState.Locked)
        private val password = MutableStateFlow("")

        private val securityState = dataStoreRepository.readSecurityState()

        val isLocked =
            combine(lockState, securityState) { lockState, securityState ->
                if (securityState.lockType is LockScreenType.None) {
                    false
                } else {
                    lockState !is LockState.Unlocked
                }
            }
                .stateIn(
                    initialValue = false,
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        val uiState: StateFlow<LockUiState> =
            combine(
                securityState,
                password,
                lockState,
                errorMessage,
            ) { securityState, password, lockState, errorMessage ->
                LockUiState(
                    securityState = securityState,
                    password = password,
                    lockState = lockState,
                    errorMessage = errorMessage,
                )
            }
                .stateIn(
                    initialValue = LockUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
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
                    NumberKeyboardKeys.KEY_BACKSPACE -> {
                        password.update { state -> state.dropLast(1) }
                    }
                    NumberKeyboardKeys.KEY_ENTER -> {
                        enterPassword()
                    }
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
    val securityState: SecurityState = SecurityState.initialInstance(),
    val errorMessage: String = "",
    val password: String = "",
    val lockState: LockState = LockState.Idle,
)

sealed interface LockState {
    data object WrongPin : LockState

    data object Unlocked : LockState

    data object Locked : LockState

    data object Idle : LockState
}
