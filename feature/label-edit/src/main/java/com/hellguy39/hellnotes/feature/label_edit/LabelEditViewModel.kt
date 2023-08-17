package com.hellguy39.hellnotes.feature.label_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.label.DeleteLabelUseCase
import com.hellguy39.hellnotes.core.domain.use_case.label.GetAllLabelsStreamUseCase
import com.hellguy39.hellnotes.core.domain.use_case.label.InsertLabelUseCase
import com.hellguy39.hellnotes.core.domain.use_case.label.UpdateLabelUseCase
import com.hellguy39.hellnotes.core.model.local.database.Label
import com.hellguy39.hellnotes.core.ui.model.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelEditViewModel @Inject constructor(
    getAllLabelsStreamUseCase: GetAllLabelsStreamUseCase,
    private val insertLabelUseCase: InsertLabelUseCase,
    private val updateLabelUseCase: UpdateLabelUseCase,
    private val deleteLabelUseCase: DeleteLabelUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val action = savedStateHandle.get<String>(ArgumentKeys.Action) ?: ""

    val uiState: StateFlow<LabelEditUiState> = getAllLabelsStreamUseCase.invoke()
        .map { labels ->
            LabelEditUiState.Success(
                labels = labels.sortedByDescending { label -> label.id },
                action = action,
            )
        }
        .stateIn(
            initialValue = LabelEditUiState.Loading,
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
            insertLabelUseCase.invoke(label)
        }
    }

    private fun deleteLabel(label: Label) {
        viewModelScope.launch {
            deleteLabelUseCase.invoke(label)
        }
    }

    private fun updateLabel(label: Label) {
        viewModelScope.launch {
            updateLabelUseCase.invoke(label)
        }
    }

}

sealed interface LabelEditUiState {

    data object Loading: LabelEditUiState

    data class Success(
        val action: String,
        val labels: List<Label>
    ) : LabelEditUiState

}

sealed class LabelEditScreenUiEvent {

    data class InsertLabel(val label: Label): LabelEditScreenUiEvent()

    data class DeleteLabel(val label: Label): LabelEditScreenUiEvent()

    data class UpdateLabel(val label: Label): LabelEditScreenUiEvent()

}