package com.hellguy39.hellnotes.feature.home.label

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.common.arguments.Arguments
import com.hellguy39.hellnotes.core.common.arguments.getArgument
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.domain.usecase.note.GetAllNotesWithRemindersAndLabelsStreamUseCase
import com.hellguy39.hellnotes.core.model.*
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import com.hellguy39.hellnotes.core.ui.NoteCategory
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelViewModel
    @Inject
    constructor(
        private val labelRepository: LabelRepository,
        getAllNotesWithRemindersAndLabelsStreamUseCase: GetAllNotesWithRemindersAndLabelsStreamUseCase,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val labelId = savedStateHandle.getArgument(Arguments.LabelId)

        val uiState: StateFlow<LabelUiState> =
            combine(
                labelRepository.getLabelByIdFlow(labelId),
                labelRepository.getAllLabelsStream(),
                getAllNotesWithRemindersAndLabelsStreamUseCase.invoke(),
            ) { label, labels, notes ->
                val wrappers =
                    notes
                        .filter { wrapper -> !wrapper.note.isArchived }
                        .filter { wrapper -> wrapper.labels.contains(label) }
                LabelUiState(
                    isEmpty = wrappers.isEmpty(),
                    noteCategories =
                        mutableStateListOf(
                            NoteCategory(
                                notes = wrappers.toStateList(),
                            ),
                        ),
                    label = label ?: Label(),
                    allLabels = labels.toStateList(),
                )
            }
                .stateIn(
                    initialValue = LabelUiState(),
                    started = SharingStarted.WhileSubscribed(5_000),
                    scope = viewModelScope,
                )

        fun send(uiEvent: LabelUiEvent) {
            when (uiEvent) {
                is LabelUiEvent.DeleteLabel -> {
                    deleteLabel()
                }
                is LabelUiEvent.RenameLabel -> {
                    renameLabel(uiEvent.name)
                }
            }
        }

        private fun deleteLabel() {
            viewModelScope.launch {
                val label = uiState.value.label
                labelRepository.deleteLabel(label)
            }
        }

        private fun renameLabel(name: String) {
            viewModelScope.launch {
                val label = uiState.value.label.copy(name = name)
                labelRepository.updateLabel(label)
            }
        }

        fun isLabelUnique(name: String) =
            uiState.value.allLabels
                .find { label -> label.name == name } == null
    }

sealed class LabelUiEvent {
    data class RenameLabel(val name: String) : LabelUiEvent()

    data object DeleteLabel : LabelUiEvent()
}

data class LabelUiState(
    val isEmpty: Boolean = false,
    val label: Label = Label(),
    val allLabels: SnapshotStateList<Label> = mutableStateListOf(),
    val noteCategories: SnapshotStateList<NoteCategory> = mutableStateListOf(),
)
