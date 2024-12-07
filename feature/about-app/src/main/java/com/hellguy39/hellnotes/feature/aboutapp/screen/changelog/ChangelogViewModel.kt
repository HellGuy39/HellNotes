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
package com.hellguy39.hellnotes.feature.aboutapp.screen.changelog

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.ui.extensions.toStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChangelogViewModel
    @Inject
    constructor(
        private val githubRepositoryService: GithubRepositoryService,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<ChangelogUiState> = MutableStateFlow(ChangelogUiState())
        val uiState = _uiState.asStateFlow()

        init {
            fetchReleases()
        }

        fun send(uiEvent: ChangelogUiEvent) {
            when (uiEvent) {
                ChangelogUiEvent.TryAgain -> {
                    fetchReleases()
                }
            }
        }

        private fun fetchReleases() {
            viewModelScope.launch {
                if (_uiState.value.isLoading) return@launch

                githubRepositoryService.getReleases().collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _uiState.update { state ->
                                state.copy(
                                    releases = resource.data?.toStateList() ?: mutableStateListOf(),
                                    isError = false,
                                    errorMessage = "",
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _uiState.update { state ->
                                state.copy(isLoading = resource.isLoading)
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    releases = resource.data?.toStateList() ?: mutableStateListOf(),
                                    isError = true,
                                    errorMessage = resource.message ?: "",
                                )
                            }
                        }
                    }
                }
            }
        }
    }

sealed class ChangelogUiEvent {
    data object TryAgain : ChangelogUiEvent()
}

data class ChangelogUiState(
    val releases: SnapshotStateList<Release> = mutableStateListOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)
