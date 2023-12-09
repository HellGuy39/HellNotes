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
