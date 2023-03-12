package com.hellguy39.hellnotes.activity.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.SecurityState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _isOnBoardingCompleted: MutableState<Boolean> = mutableStateOf(false)
    val isOnBoardingCompleted: State<Boolean> = _isOnBoardingCompleted

    val securityState = dataStoreRepository.readSecurityState()
        .stateIn(
            initialValue = SecurityState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    init {
        viewModelScope.launch {
            dataStoreRepository.readOnBoardingState().collect { completed ->
                _isOnBoardingCompleted.value = completed
            }
            _isLoading.value = false
        }
    }

}