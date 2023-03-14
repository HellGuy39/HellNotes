package com.hellguy39.hellnotes.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.SecurityState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    val splashState: StateFlow<SplashState> =
        combine(
            dataStoreRepository.readSecurityState(),
            dataStoreRepository.readOnBoardingState()
        ) { securityState, onBoardingState ->

            val isLoading = securityState.hashCode() == SecurityState.initialInstance().hashCode()

            SplashState(
                securityState = securityState,
                onBoardingState = onBoardingState,
                isLoading = isLoading
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SplashState.initialInstance()
            )

}

data class SplashState(
    val isLoading: Boolean,
    val securityState: SecurityState,
    val onBoardingState: Boolean
) {
    companion object {
        fun initialInstance() = SplashState(
            isLoading = true,
            securityState = SecurityState.initialInstance(),
            onBoardingState = false
        )
    }
}