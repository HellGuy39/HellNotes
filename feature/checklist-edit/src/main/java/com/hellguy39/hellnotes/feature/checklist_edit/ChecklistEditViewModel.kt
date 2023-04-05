package com.hellguy39.hellnotes.feature.checklist_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.ChecklistRepository
import com.hellguy39.hellnotes.core.model.Checklist
import com.hellguy39.hellnotes.core.model.ChecklistItem
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentDefaultValues
import com.hellguy39.hellnotes.core.ui.navigations.ArgumentKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChecklistEditViewModel @Inject constructor(
    private val checklistRepository: ChecklistRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val checklistId = savedStateHandle.get<Long>(ArgumentKeys.ChecklistId) ?: 0

    private val noteId = savedStateHandle.get<Long>(ArgumentKeys.NoteId) ?: 0

    private val _checklist: MutableStateFlow<Checklist> = MutableStateFlow(Checklist.initialInstance())
    val checklist = _checklist.asStateFlow()

    init {
        viewModelScope.launch {
            val list = if(checklistId == ArgumentDefaultValues.NewChecklist) {
                val id = checklistRepository.insertChecklist(
                    Checklist.initialInstance(noteId = noteId)
                )
                checklistRepository.getChecklistById(id)
            } else {
                checklistRepository.getChecklistById(checklistId)
            }

            _checklist.update { list }
        }
    }

    fun send(uiEvent: ChecklistEditUiEvent) {
        when(uiEvent) {
            is ChecklistEditUiEvent.OnAddItem -> {
                addItem()
            }
            is ChecklistEditUiEvent.OnMoveItem -> {
                moveItem(uiEvent.startPos, uiEvent.endPos)
            }
            is ChecklistEditUiEvent.OnRemoveItem -> {
                removeItem(uiEvent.item)
            }
            is ChecklistEditUiEvent.OnUpdateItemText -> {
                updateItem(uiEvent.item, uiEvent.text)
            }
            is ChecklistEditUiEvent.OnClose -> {
                discardChecklistIfEmptyOrSave()
            }
        }
    }

    private fun addItem() {
        viewModelScope.launch {
            _checklist.update { checklist ->
                val items = checklist.items.toMutableList().apply {
                    add(ChecklistItem.newInstance())
                }
                checklist.copy(items = items)
            }
        }
    }

    private fun removeItem(item: ChecklistItem) {
        viewModelScope.launch {
            _checklist.update { checklist ->
                val items = checklist.items.minus(item)
                checklist.copy(items = items)
            }
        }
    }

    private fun updateItem(item: ChecklistItem, text: String) {
        viewModelScope.launch {
            val index = _checklist.value.items.indexOf(item)
            val items = _checklist.value.items.toMutableList().apply {
                this[index] = item.copy(text = text)
            }
            _checklist.update { checklist ->
                checklist.copy(items = items)
            }
        }
    }

    private fun moveItem(from: Int, to: Int) {
        val items = _checklist.value.items.toMutableList().apply {
            add(to, removeAt(from))
        }
        _checklist.update { checklist ->
            checklist.copy(items = items)
        }
    }

    private fun discardChecklistIfEmptyOrSave() {
        viewModelScope.launch {
            _checklist.value.let { checklist ->
                if (checklist.items.isEmpty()) {
                    checklistRepository.deleteChecklist(checklist)
                } else {
                    checklistRepository.updateChecklist(checklist)
                }
            }
        }
    }

}

sealed class ChecklistEditUiEvent {
    object OnAddItem: ChecklistEditUiEvent()
    object OnClose: ChecklistEditUiEvent()
    data class OnRemoveItem(val item: ChecklistItem) : ChecklistEditUiEvent()
    data class OnMoveItem(val startPos: Int, val endPos: Int): ChecklistEditUiEvent()
    data class OnUpdateItemText(val item: ChecklistItem, val text: String): ChecklistEditUiEvent()
}