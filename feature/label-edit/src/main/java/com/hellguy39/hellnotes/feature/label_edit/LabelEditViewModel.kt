package com.hellguy39.hellnotes.feature.label_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.model.Label
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelEditViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val labels = labelRepository.getAllLabelsStream()
        .stateIn(
            initialValue = listOf(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    private val labelEditViewModel = MutableStateFlow(
        LabelViewModelState(action = savedStateHandle.get<String>(ArgumentKeys.Action) ?: "")
    )

    val uiState = labelEditViewModel
        .combine(
            labels
        ) { viewModelState, labels ->
            viewModelState.toUiState(labels = labels, isLoading = false)
        }
        .stateIn(
            initialValue = labelEditViewModel.value.toUiState(isLoading = true),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    fun insertLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.insertLabel(label)
        }
    }

    fun deleteLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.deleteLabel(label)
        }
    }

    fun updateLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.updateLabel(label)
        }
    }

}

private data class LabelViewModelState(
    val action: String = "",
) {
    fun toUiState(
        labels: List<Label> = listOf(),
        isLoading: Boolean
    ) = LabelEditUiState(
        labels = labels.sortedByDescending { label -> label.id },
        action = action,
        isLoading = isLoading
    )
}

data class LabelEditUiState(
    val labels: List<Label>,
    val action: String,
    val isLoading: Boolean
)