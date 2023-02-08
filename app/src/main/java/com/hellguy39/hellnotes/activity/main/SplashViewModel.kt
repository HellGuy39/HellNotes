package com.hellguy39.hellnotes.activity.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _isOnBoardingCompleted: MutableState<Boolean> = mutableStateOf(false)
    val isOnBoardingCompleted: State<Boolean> = _isOnBoardingCompleted

    private val _isAppLocked: MutableState<Boolean> = mutableStateOf(false)
    val isAppLocked: State<Boolean> = _isAppLocked

    init {
        viewModelScope.launch {
            dataStoreRepository.readAppSettings().collect { settings ->
                _isAppLocked.value = settings.isAppLocked
                _isOnBoardingCompleted.value = settings.isOnBoardingCompleted
            }
            _isLoading.value = false
        }
    }

}