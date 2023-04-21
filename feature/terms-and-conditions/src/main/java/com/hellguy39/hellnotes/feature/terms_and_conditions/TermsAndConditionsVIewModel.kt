package com.hellguy39.hellnotes.feature.terms_and_conditions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.remote.GithubRepositoryService
import com.hellguy39.hellnotes.core.model.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermsAndConditionsViewModel @Inject constructor(
    private val githubRepositoryService: GithubRepositoryService
): ViewModel() {

    private val _uiState = MutableStateFlow(TermsAndConditionsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchTermsAndConditions()
    }

    fun send(uiEvent: TermsAndConditionsUiEvent) {
        when(uiEvent) {
            TermsAndConditionsUiEvent.TryAgain -> {
                fetchTermsAndConditions()
            }
        }
    }

    private fun fetchTermsAndConditions() {
        viewModelScope.launch {
            githubRepositoryService.getTermsAndConditions().collect { resource ->
                when(resource) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                termsAndConditions = resource.data ?: "",
                                isError = false,
                                errorMessage = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(
                                termsAndConditions = resource.data ?: "",
                                isError = true,
                                errorMessage = resource.message ?: ""
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = resource.isLoading
                            )
                        }
                    }
                }
            }
        }
    }

}

sealed class TermsAndConditionsUiEvent {
    object TryAgain: TermsAndConditionsUiEvent()
}

data class TermsAndConditionsUiState(
    val termsAndConditions: String = "",
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = ""
)