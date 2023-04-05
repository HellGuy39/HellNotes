package com.hellguy39.hellnotes.feature.lock_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSelectionViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    val uiState: StateFlow<LockSelectionUiState> = dataStoreRepository.readSecurityState()
        .map { securityState ->
            LockSelectionUiState(
                securityState = securityState
            )
        }
        .stateIn(
            initialValue = LockSelectionUiState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    fun resetAppLock() {
        viewModelScope.launch {
            dataStoreRepository.saveSecurityState(
                uiState.value.securityState.copy(
                    lockType = LockScreenType.None,
                    password = ""
                )
            )
        }
    }
}

data class LockSelectionUiState(
    val securityState: SecurityState
) {
    companion object {
        fun initialInstance() = LockSelectionUiState(
            securityState = SecurityState.initialInstance()
        )
    }
}
