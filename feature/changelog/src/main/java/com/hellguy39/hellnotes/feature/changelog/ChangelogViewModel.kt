package com.hellguy39.hellnotes.feature.changelog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangelogViewModel
@Inject
constructor(
    private val githubRepositoryService: GithubRepositoryService
): ViewModel() {

    private val _uiState: MutableStateFlow<ChangelogUiState> = MutableStateFlow(ChangelogUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchReleases()
    }

    fun send(uiEvent: ChangelogUiEvent) {
        when(uiEvent) {
            ChangelogUiEvent.TryAgain -> {
                fetchReleases()
            }
        }
    }

    private fun fetchReleases() {
        viewModelScope.launch {

            if (_uiState.value.isLoading) return@launch

            githubRepositoryService.getReleases().collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                releases = resource.data ?: emptyList(),
                                isError = false,
                                errorMessage = ""
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
                                releases = resource.data ?: emptyList(),
                                isError = true,
                                errorMessage = resource.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class ChangelogUiEvent {
    data object TryAgain: ChangelogUiEvent()
}

data class ChangelogUiState (
    val releases: List<Release> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)