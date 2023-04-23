package com.hellguy39.hellnotes.feature.backup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.use_case.BackupDatabaseUseCase
import com.hellguy39.hellnotes.core.domain.use_case.RestoreDatabaseUseCase
import com.hellguy39.hellnotes.core.model.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository,
    private val backupDatabaseUseCase: BackupDatabaseUseCase,
    private val restoreDatabaseUseCase: RestoreDatabaseUseCase
): ViewModel() {

    private val isLoading = MutableStateFlow(false)

    val uiState = combine(
        isLoading,
        dataStoreRepository.readLastBackupDate()
    ) { isLoading, lastBackupDate ->
        BackupUiState(
            isLoading = isLoading,
            lastBackupDate = lastBackupDate
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = BackupUiState()
        )

    fun send(uiEvent: BackupUiEvent) {
        when(uiEvent) {
            is BackupUiEvent.Backup -> {
                backupDatabase(filepath = uiEvent.filepath)
            }
            is BackupUiEvent.Restore -> {
                restoreDatabase(filepath = uiEvent.filepath)
            }
        }
    }

    private fun restoreDatabase(filepath: Uri) {
        viewModelScope.launch {
            try {
                restoreDatabaseUseCase.invoke(filepath).collect { resource ->
                    when(resource) {
                        is Resource.Loading -> { isLoading.update { resource.isLoading } }
                        is Resource.Success -> {}
                        is Resource.Error -> {}
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun backupDatabase(filepath: Uri) {
        viewModelScope.launch {
            try {
                backupDatabaseUseCase.invoke(filepath).collect { resource ->
                    when(resource) {
                        is Resource.Loading -> { isLoading.update { resource.isLoading } }
                        is Resource.Success -> {}
                        is Resource.Error -> {}
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

}

data class BackupUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val lastBackupDate: Long = 0
)

sealed class BackupUiEvent {

    data class Restore(val filepath: Uri): BackupUiEvent()

    data class Backup(val filepath: Uri): BackupUiEvent()

}