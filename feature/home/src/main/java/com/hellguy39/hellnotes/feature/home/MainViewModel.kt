package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.note.InsertNoteUseCase
import com.hellguy39.hellnotes.core.model.local.database.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
): ViewModel() {

    private val _openedNoteId: MutableStateFlow<Long> = MutableStateFlow(Note.EMPTY_ID)
    val openedNoteId = _openedNoteId.asStateFlow()

    private val _isDetailOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDetailOpen = _isDetailOpen.asStateFlow()

    fun onEvent(uiEvent: MainUiEvent) {
        when(uiEvent) {
            is MainUiEvent.OpenNoteEditing -> {
                _openedNoteId.update { uiEvent.noteId }
                _isDetailOpen.update { true }
            }
            MainUiEvent.CloseNoteEditing -> {
                _openedNoteId.update { Note.EMPTY_ID }
                _isDetailOpen.update { false }
            }
            MainUiEvent.CreateNewNoteAndOpenEditing -> {
                viewModelScope.launch {
                    val newNoteId = insertNoteUseCase.invoke(Note())
                    _openedNoteId.update { newNoteId }
                    _isDetailOpen.update { true }
                }
            }
        }
    }
}

sealed class MainUiEvent {

    data class OpenNoteEditing(val noteId: Long): MainUiEvent()

    data object CloseNoteEditing: MainUiEvent()

    data object CreateNewNoteAndOpenEditing: MainUiEvent()

}

fun MainViewModel.isDetailOpen(): Boolean {
    return openedNoteId.value != Note.EMPTY_ID
}

//    private val selectedNoteWrapper = combine(
//            note, reminds, labels, checklists
//        ) { note, reminders, labels, checklists ->
//            note.toNoteWrapper(labels = labels, reminders = reminders, checklists = checklists)
//        }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = NoteWrapper(Note())
//            )
//
//    val uiState = combine(
//        noteListState, searchState, selectedNoteWrapper, openedNoteId, mainDialogStates
//    ) { noteListState, searchState, selectedNoteWrapper, openedNoteId, mainDialogStates ->
//        MainUiState(
//            noteWrapperState = when(openedNoteId) {
//                Note.EMPTY_ID -> NoteWrapperState.Empty
//                else -> NoteWrapperState.Success(selectedNoteWrapper)
//            },
//            listState = noteListState,
//            searchState = searchState,
//            dialogStates = mainDialogStates
//        )
//    }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = MainUiState()
//        )
//
//    fun onEvent(uiEvent: MainUiEvent) {
//        when(uiEvent) {
//            is MainUiEvent.OpenNoteEditing -> {
//                if (_openedNoteId.value != Note.EMPTY_ID) {
//                    closeNoteEditing()
//                }
//                _openedNoteId.update { uiEvent.noteId }
//                fetchEditableNote(uiEvent.noteId)
//            }
//            is MainUiEvent.CloseNoteEditing -> closeNoteEditing()
//
//            is MainUiEvent.CreateNewNoteAndOpenEditing -> {
//                viewModelScope.launch {
//                    val id = insertNoteUseCase.invoke(Note())
//                    _openedNoteId.update { id }
//                    fetchEditableNote(id)
//                }
//            }
//            is MainUiEvent.ChangeSearchIsActive -> {
//                searchState.update { state -> state.copy(isActive = uiEvent.isActive) }
//            }
//            is MainUiEvent.ChangeSearchQuery -> {
//                searchState.update { state -> state.copy(query = uiEvent.query) }
//            }
//            is MainUiEvent.Search -> {
//
//            }
//            is MainUiEvent.ChangeNoteContent -> {
//                note.update { note -> note.copy(note = uiEvent.text, editedAt = System.currentTimeMillis()) }
//                saveEditableNote()
//            }
//            is MainUiEvent.ChangeNoteTitle -> {
//                note.update { note -> note.copy(title = uiEvent.title, editedAt = System.currentTimeMillis()) }
//                saveEditableNote()
//            }
//            is MainUiEvent.OpenAttachmentBottomSheet -> {
//                isAttachmentBottomSheetOpen.update { uiEvent.isOpen }
//            }
//            is MainUiEvent.OpenDeleteAlertDialog -> {
//                isDeleteAlertDialogOpen.update { uiEvent.isOpen }
//            }
//            is MainUiEvent.OpenMenuBottomSheet -> {
//                isMenuBottomSheetOpen.update { uiEvent.isOpen }
//            }
//            is MainUiEvent.OpenReminderEditDialog -> {
//                reminderEditDialogState.update { state -> state.copy(isOpen = uiEvent.isOpen) }
//            }
//        }
//    }
//
//    private fun closeNoteEditing() {
//        saveEditableNoteOrDeleteIfEmpty()
//        _openedNoteId.update { Note.EMPTY_ID }
//    }
//
//    private fun fetchEditableNote(noteId: Long) {
//        viewModelScope.launch {
//            if (noteId == Note.EMPTY_ID) {
//                note.update { Note(Note.EMPTY_ID) }
//            } else {
//                val note = getNoteByIdUseCase.invoke(noteId)
//                val checklists = checklistRepository.getChecklistsByNoteId(noteId)
//
//                this@MainViewModel.note.update { note }
//                this@MainViewModel.checklists.update { checklists }
//            }
//        }
//    }
//
//    private fun saveEditableNote() {
//        viewModelScope.launch {
//            val state = uiState.value
//
//            if (state.noteWrapperState !is NoteWrapperState.Success)
//                return@launch
//
//            val postProcessedNote = prepareNoteForSave(state.noteWrapperState.noteWrapper)
//            updateNoteUseCase.invoke(postProcessedNote.note)
//        }
//    }
//
//    private suspend fun prepareNoteForSave(noteWrapper: NoteWrapper): NoteWrapper {
//        var postProcessedNote = postProcessNoteUseCase.invoke(noteWrapper)
//        val invalidChecklists = mutableListOf<Checklist>()
//
//        for (i in postProcessedNote.checklists.indices) {
//            val checklist = postProcessedNote.checklists[i]
//
//            if (checklist.isChecklistValid()) {
//                checklistRepository.updateChecklist(checklist)
//            } else {
//                invalidChecklists.add(checklist)
//                checklist.id?.let { id -> checklistRepository.deleteChecklistById(id) }
//            }
//        }
//
//        postProcessedNote = postProcessedNote.copy(
//            checklists = postProcessedNote.checklists.toMutableList()
//                .apply { removeAll(invalidChecklists) }
//        )
//
//        return postProcessedNote
//    }
//
//    private fun saveEditableNoteOrDeleteIfEmpty() {
//        viewModelScope.launch {
//            val state = uiState.value
//
//            if (state.noteWrapperState !is NoteWrapperState.Success)
//                return@launch
//
//            val postProcessedNote = prepareNoteForSave(state.noteWrapperState.noteWrapper)
//            if (postProcessedNote.isNoteWrapperInvalid()) {
//                deleteNote()
//            } else {
//                saveEditableNote()
//            }
//        }
//    }
//
//    private fun deleteNote() {
//        viewModelScope.launch {
//            val note = note.value
//            deleteNoteUseCase.invoke(note)
//        }
//    }
//
//    companion object {
//
//        fun isDetailOpen(noteId: Long): Boolean {
//            return noteId != Note.EMPTY_ID
//        }
//    }
//}
//
//fun List<NoteWrapper>.applyQueryFilter(q: String): List<NoteWrapper> {
//
//    if (q.isEmpty())
//        return this
//
//    return this.filter { noteWrapper ->
//        noteWrapper.note.note.contains(q, true) ||
//                noteWrapper.note.title.contains(q, true)
//    }
//}
//data class SearchState(
//    val query: String = "",
//    val isActive: Boolean = false
//)

//sealed class MainUiEvent {
//
//    data object CloseNoteEditing: MainUiEvent()
//
//    data class OpenNoteEditing(val noteId: Long): MainUiEvent()
//
//    data object CreateNewNoteAndOpenEditing: MainUiEvent()
//
//    data class ChangeSearchQuery(val query: String): MainUiEvent()
//
//    data class Search(val query: String): MainUiEvent()
//
//    data class ChangeSearchIsActive(val isActive: Boolean): MainUiEvent()
//
//    data class ChangeNoteTitle(val title: String): MainUiEvent()
//
//    data class ChangeNoteContent(val text: String): MainUiEvent()
//
//    data class OpenMenuBottomSheet(val isOpen: Boolean): MainUiEvent()
//
//    data class OpenAttachmentBottomSheet(val isOpen: Boolean): MainUiEvent()
//
//    data class OpenDeleteAlertDialog(val isOpen: Boolean): MainUiEvent()
//
//    data class OpenReminderEditDialog(val isOpen: Boolean): MainUiEvent()
//
//}

//sealed class NoteWrapperState {
//
//    data class Success(val noteWrapper: NoteWrapper) : NoteWrapperState()
//
//    data object Empty : NoteWrapperState()
//
//}
//
//data class MainDialogStates(
//    val reminderEditDialogState: ReminderEditDialogState = ReminderEditDialogState(),
//    val isMenuBottomSheetOpen: Boolean = false,
//    val isAttachmentBottomSheetOpen: Boolean = false,
//    val isDeleteAlertDialogOpen: Boolean = false
//)
//
//data class ReminderEditDialogState(
//    val isOpen: Boolean = false,
//    val reminder: Reminder? = null
//)
