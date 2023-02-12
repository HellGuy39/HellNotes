package com.hellguy39.hellnotes.feature.lock_setup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
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
            dataStoreRepository.saveAppCode(code)
            dataStoreRepository.saveAppLockType(lockSetupViewModel.value.newLockScreenType)
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