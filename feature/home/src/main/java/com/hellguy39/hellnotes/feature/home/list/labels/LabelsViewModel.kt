package com.hellguy39.hellnotes.feature.home.list.labels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.label.GetAllLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.local.database.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LabelsViewModel @Inject constructor(
    getAllLabelsStreamUseCase: GetAllLabelsStreamUseCase
): ViewModel() {

    val uiState: StateFlow<LabelsUiState> = getAllLabelsStreamUseCase.invoke()
        .map { labels ->
            if (labels.isNotEmpty())
                LabelsUiState.Success(labels)
            else
                LabelsUiState.Empty
        }
        .stateIn(
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LabelsUiState.Idle,
            scope = viewModelScope
        )

}

sealed class LabelsUiState {

    data object Idle: LabelsUiState()

    data object Empty: LabelsUiState()

    data class Success(val labels: List<Label>): LabelsUiState()
}