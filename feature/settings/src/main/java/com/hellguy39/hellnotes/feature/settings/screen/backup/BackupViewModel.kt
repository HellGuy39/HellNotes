/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.settings.screen.backup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.settings.DataStoreRepository
import com.hellguy39.hellnotes.core.domain.usecase.backup.BackupDatabaseUseCase
import com.hellguy39.hellnotes.core.domain.usecase.backup.RestoreDatabaseUseCase
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel
    @Inject
    constructor(
        dataStoreRepository: DataStoreRepository,
        private val backupDatabaseUseCase: BackupDatabaseUseCase,
        private val restoreDatabaseUseCase: RestoreDatabaseUseCase,
    ) : ViewModel() {
        private val backupViewModelState = MutableStateFlow(BackupViewModelState())

        val uiState =
            combine(
                backupViewModelState,
                dataStoreRepository.readLastBackupDate(),
            ) { backupViewModelState, lastBackupDate ->
                BackupUiState(
                    isLoading = backupViewModelState.isLoading,
                    lastBackupDate = lastBackupDate,
                    message = backupViewModelState.message,
                )
            }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = BackupUiState(),
                )

        fun send(uiEvent: BackupUiEvent) {
            when (uiEvent) {
                is BackupUiEvent.Backup -> backupDatabase(filepath = uiEvent.filepath)

                is BackupUiEvent.Restore -> restoreDatabase(filepath = uiEvent.filepath)
            }
        }

        private fun restoreDatabase(filepath: Uri) {
            viewModelScope.launch {
                restoreDatabaseUseCase.invoke(filepath).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            backupViewModelState.update { state -> state.copy(isLoading = resource.isLoading) }
                        }
                        is Resource.Success -> {
                            backupViewModelState.update { state ->
                                state.copy(
                                    message =
                                        UiText
                                            .StringResources(AppStrings.Snack.StorageRestored),
                                )
                            }
                        }
                        is Resource.Error -> {
                            backupViewModelState.update { state ->
                                state.copy(
                                    message = UiText.DynamicString(resource.message.toString()),
                                )
                            }
                        }
                    }
                }
            }
        }

        private fun backupDatabase(filepath: Uri) {
            viewModelScope.launch {
                backupDatabaseUseCase.invoke(filepath).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            backupViewModelState.update { state -> state.copy(isLoading = resource.isLoading) }
                        }
                        is Resource.Success -> {
                            backupViewModelState.update { state ->
                                state.copy(
                                    message =
                                        UiText
                                            .StringResources(AppStrings.Snack.BackupCreated),
                                )
                            }
                        }
                        is Resource.Error -> {
                            backupViewModelState.update { state ->
                                state.copy(
                                    message = UiText.DynamicString(resource.message.toString()),
                                )
                            }
                        }
                    }
                }
            }
        }
    }

data class BackupViewModelState(
    val isLoading: Boolean = false,
    val message: UiText = UiText.Empty,
)

data class BackupUiState(
    val isLoading: Boolean = false,
    val message: UiText = UiText.Empty,
    val lastBackupDate: Long = 0,
)

sealed class BackupUiEvent {
    data class Restore(val filepath: Uri) : BackupUiEvent()

    data class Backup(val filepath: Uri) : BackupUiEvent()
}
