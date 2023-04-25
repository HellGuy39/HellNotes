package com.hellguy39.hellnotes.feature.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.CheckForUpdatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val checkForUpdatesUseCase: CheckForUpdatesUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<UpdateUiState> = MutableStateFlow(UpdateUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            checkForUpdatesUseCase.invoke()
        }
    }

}

sealed class UpdateUiState {

    object Loading: UpdateUiState()

    data class UpdateAvailable(val version: String, val url: String): UpdateUiState()

    object NoUpdates: UpdateUiState()

}