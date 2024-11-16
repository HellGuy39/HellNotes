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
package com.hellguy39.hellnotes.feature.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.domain.repository.system.Downloader
import com.hellguy39.hellnotes.core.domain.usecase.CheckForUpdatesUseCase
import com.hellguy39.hellnotes.core.model.Resource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel
    @Inject
    constructor(
        private val githubRepositoryService: GithubRepositoryService,
        private val checkForUpdatesUseCase: CheckForUpdatesUseCase,
        private val downloader: Downloader,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<UpdateUiState> = MutableStateFlow(UpdateUiState())
        val uiState = _uiState.asStateFlow()

        init {
            viewModelScope.launch {
                githubRepositoryService.getReleases().collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let { releases ->
                                val latestRelease = releases.getLatestRelease()

                                if (checkForUpdatesUseCase.invoke(latestRelease)) {
                                    _uiState.update { state ->
                                        state.copy(
                                            message = UiText.DynamicString("Update available ${latestRelease.tagName}"),
                                            update = Update.Available(latestRelease),
                                        )
                                    }
                                } else {
                                    _uiState.update { state ->
                                        state.copy(
                                            message = UiText.DynamicString("Latest version is already installed"),
                                            update = Update.NoNeed,
                                        )
                                    }
                                }
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    message = UiText.DynamicString("Failed to check updates"),
                                    update = Update.Failed,
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _uiState.update { state ->
                                state.copy(isLoading = resource.isLoading)
                            }
                        }
                    }
                }
            }
        }

        fun send(uiEvent: UpdateUiEvent) {
            when (uiEvent) {
                UpdateUiEvent.DownloadUpdate -> {
                    _uiState.value.update.let { update ->
                        if (update !is Update.Available) {
                            return@let
                        }

                        update.release.assets?.get(0)?.browserDownloadUrl?.let { url ->
                            downloader.downloadFile(url)
                        }
                    }
                }
            }
        }

        private fun List<Release>.getLatestRelease(): Release {
            return sortedByDescending { release -> release.publishedAt }.first()
        }
    }

sealed class UpdateUiEvent() {
    object DownloadUpdate : UpdateUiEvent()
}

data class UpdateUiState(
    val isLoading: Boolean = false,
    val message: UiText = UiText.Empty,
    val update: Update = Update.Idle,
)

sealed class Update {
    data class Available(val release: Release) : Update()

    object NoNeed : Update()

    object Failed : Update()

    object Idle : Update()
}
