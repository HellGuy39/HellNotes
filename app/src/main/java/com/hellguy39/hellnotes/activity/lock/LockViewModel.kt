package com.hellguy39.hellnotes.activity.lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LockViewModel @Inject constructor(
    appSettingsRepository: AppSettingsRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<LockUiState> = MutableStateFlow(LockUiState.Locked)
    val uiState = _uiState.asStateFlow()

    private val _pin: MutableStateFlow<String> = MutableStateFlow("")
    val pin = _pin.asStateFlow()

    private val appSettings = appSettingsRepository.getAppSettings()

    fun enterKey(key: String) = viewModelScope.launch {
        when (key) {
            LockActivity.KEY_BACKSPACE -> {
                _pin.update { it.dropLast(1) }
            }
            else -> {
                _pin.update { it.plus(key) }
            }
        }
        checkPin()
    }

    fun clearPin() = viewModelScope.launch {
        _pin.update { "" }
    }

    private fun checkPin() {
        _pin.value.let { pin ->
            if (pin.length >= 4) {
                if (pin == appSettings.appPin) {
                    _uiState.update { LockUiState.Unlocked }
                } else {
                    _uiState.update { LockUiState.WrongPin }
                    clearPin()
                }
            } else {
                _uiState.update { LockUiState.Locked }
            }
        }
    }

    fun authByBiometric() = viewModelScope.launch {
        _uiState.update { LockUiState.Unlocked }
    }

    fun isAppLocked() = appSettings.isPinSetup
    fun isUseBiometric() = appSettings.isUseBiometric
}

sealed interface LockUiState {
    object WrongPin : LockUiState
    object Unlocked : LockUiState
    object Locked : LockUiState
}