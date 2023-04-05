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

    val uiState: StateFlow<LockSetupUiState> =
        combine(
            dataStoreRepository.readSecurityState(),
            savedStateHandle.getStateFlow(ArgumentKeys.LockType, LockScreenType.None.string())
        ) { securityState, newLockScreenType ->
            LockSetupUiState(
                securityState = securityState,
                newLockScreenType = LockScreenType.from(newLockScreenType)
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = LockSetupUiState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun saveAppCode(code: String) {
        viewModelScope.launch {
            val securityState = uiState.value.securityState
            val newLockScreenType = uiState.value.newLockScreenType

            dataStoreRepository.saveSecurityState(
                securityState.copy(
                    password = code,
                    lockType = newLockScreenType
                )
            )
        }
    }

}

data class LockSetupUiState(
    val securityState: SecurityState,
    val newLockScreenType: LockScreenType
) {
    companion object {
        fun initialInstance() = LockSetupUiState(
            securityState = SecurityState.initialInstance(),
            newLockScreenType = LockScreenType.None
        )
    }
}