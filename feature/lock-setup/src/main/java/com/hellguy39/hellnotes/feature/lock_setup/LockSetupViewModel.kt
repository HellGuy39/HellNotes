package com.hellguy39.hellnotes.feature.lock_setup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSetupViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val lockSetupViewModel = MutableStateFlow(LockSetupViewModelState())

    private val securityState = dataStoreRepository.readSecurityState()
        .stateIn(
            initialValue = SecurityState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    val uiState = lockSetupViewModel
        .map(LockSetupViewModelState::toUiState)
        .stateIn(
            scope = viewModelScope,
            initialValue = lockSetupViewModel.value.toUiState(),
            started = SharingStarted.Eagerly
        )

    init {
        viewModelScope.launch {
            val lockType = LockScreenType.from(savedStateHandle.get<String>(ArgumentKeys.LockType))
            lockSetupViewModel.update { state ->
                state.copy(newLockScreenType = lockType)
            }
        }
    }

    fun saveAppCode(code: String) {
        viewModelScope.launch {
            val securityState = securityState.value
            dataStoreRepository.saveSecurityState(
                securityState.copy(
                    password = code,
                    lockType = lockSetupViewModel.value.newLockScreenType
                )
            )
        }
    }

}

private data class LockSetupViewModelState(
    val newLockScreenType: LockScreenType = LockScreenType.None
) {
    fun toUiState() = LockSetupUiState(
        newLockScreenType = newLockScreenType
    )
}

data class LockSetupUiState(
    val newLockScreenType: LockScreenType
)