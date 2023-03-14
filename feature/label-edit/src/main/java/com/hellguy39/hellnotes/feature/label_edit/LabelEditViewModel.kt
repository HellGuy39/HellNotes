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
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class LabelEditViewModel @Inject constructor(
    private val labelRepository: LabelRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val uiState: StateFlow<LabelEditUiState> = labelRepository.getAllLabelsStream()
        .map { labels ->
            LabelEditUiState(
                labels = labels.sortedByDescending { label -> label.id },
                action = savedStateHandle.get<String>(ArgumentKeys.Action) ?: "",
                isLoading = false,
            )
        }
        .stateIn(
            initialValue = LabelEditUiState.initialInstance(),
            started = SharingStarted.WhileSubscribed(5_000),
            scope = viewModelScope
        )

    fun send(uiEvent: LabelEditScreenUiEvent) {
        when(uiEvent) {
            is LabelEditScreenUiEvent.InsertLabel -> {
                insertLabel(uiEvent.label)
            }
            is LabelEditScreenUiEvent.UpdateLabel -> {
                updateLabel(uiEvent.label)
            }
            is LabelEditScreenUiEvent.DeleteLabel -> {
                deleteLabel(uiEvent.label)
            }
        }
    }

    private fun insertLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.insertLabel(label)
        }
    }

    private fun deleteLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.deleteLabel(label)
        }
    }

    private fun updateLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.updateLabel(label)
        }
    }

}

data class LabelEditUiState(
    val labels: List<Label>,
    val action: String,
    val isLoading: Boolean
) {
    companion object {
        fun initialInstance() = LabelEditUiState(
            labels = listOf(),
            action = "",
            isLoading = true
        )
    }
}

sealed class LabelEditScreenUiEvent {
    data class InsertLabel(val label: Label): LabelEditScreenUiEvent()
    data class DeleteLabel(val label: Label): LabelEditScreenUiEvent()
    data class UpdateLabel(val label: Label): LabelEditScreenUiEvent()
}