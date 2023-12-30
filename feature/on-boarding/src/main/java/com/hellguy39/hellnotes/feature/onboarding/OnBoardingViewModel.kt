package com.hellguy39.hellnotes.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel
    @Inject
    constructor(
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _onBoardingState = MutableStateFlow(OnBoardingState())
        val onBoardingState = _onBoardingState.asStateFlow()

        init {
            checkOnBoarding()
        }

        fun finishOnBoarding() {
            viewModelScope.launch {
                _onBoardingState.update { state -> state.copy(isVisible = false) }
                dataStoreRepository.saveOnBoardingState(completed = true)
            }
        }

        private fun checkOnBoarding() {
            viewModelScope.launch {
                dataStoreRepository.readOnBoardingState().collectLatest { completed ->
                    _onBoardingState.update { state -> state.copy(isVisible = !completed) }
                }
            }
        }
    }

data class OnBoardingState(
    val isVisible: Boolean = false,
)
