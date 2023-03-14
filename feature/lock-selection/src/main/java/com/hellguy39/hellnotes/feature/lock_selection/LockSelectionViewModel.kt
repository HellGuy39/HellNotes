package com.hellguy39.hellnotes.feature.lock_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.SecurityState
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSelectionViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val securityState = dataStoreRepository.readSecurityState()
        .stateIn(
            initialValue = SecurityState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    fun resetAppLock() {
        viewModelScope.launch {
            val state = securityState.value
            dataStoreRepository.saveSecurityState(
                state.copy(
                    lockType = LockScreenType.None,
                    password = ""
                )
            )
        }
    }

}
