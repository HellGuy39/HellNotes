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
package com.hellguy39.hellnotes.feature.privacypolicy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel
    @Inject
    constructor(
        private val githubRepositoryService: GithubRepositoryService,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<PrivacyPolicyUiState> = MutableStateFlow(PrivacyPolicyUiState())
        val uiState = _uiState.asStateFlow()

        init {
            fetchPrivacyPolicy()
        }

        fun send(uiEvent: PrivacyPolicyUiEvent) {
            when (uiEvent) {
                PrivacyPolicyUiEvent.TryAgain -> {
                    fetchPrivacyPolicy()
                }
            }
        }

        private fun fetchPrivacyPolicy() {
            viewModelScope.launch {
                githubRepositoryService.getPrivacyPolicy().collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            _uiState.update { state ->
                                state.copy(
                                    privacyPolicy = resource.data ?: "",
                                    isError = false,
                                    errorMessage = "",
                                )
                            }
                        }
                        is Resource.Error -> {
                            _uiState.update { state ->
                                state.copy(
                                    privacyPolicy = resource.data ?: "",
                                    isError = true,
                                    errorMessage = resource.data ?: "",
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _uiState.update { state ->
                                state.copy(
                                    isLoading = resource.isLoading,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

sealed class PrivacyPolicyUiEvent {
    object TryAgain : PrivacyPolicyUiEvent()
}

data class PrivacyPolicyUiState(
    val privacyPolicy: String = "",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
)
