package com.hellguy39.hellnotes.feature.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.SecurityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
): ViewModel() {

    val startupState: StateFlow<StartupState> =
        combine(
            dataStoreRepository.readOnBoardingState(),
            dataStoreRepository.readSecurityState()
        ) { onBoarding, security ->
            StartupState.Success(
                securityState = security,
                onBoardingState = onBoarding
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = StartupState.Loading
            )

}

sealed class StartupState {
    object Loading: StartupState()
    data class Success(
        val securityState: SecurityState,
        val onBoardingState: Boolean
    ): StartupState()
}