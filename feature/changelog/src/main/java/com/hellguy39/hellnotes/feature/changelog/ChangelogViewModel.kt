package com.hellguy39.hellnotes.feature.changelog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.remote.ReleaseService
import com.hellguy39.hellnotes.core.model.Release
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangelogViewModel @Inject constructor(
    private val releaseService: ReleaseService
): ViewModel() {

    private val _uiState: MutableStateFlow<ChangelogUiState> = MutableStateFlow(ChangelogUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val releases = releaseService.getReleases()

            _uiState.update {
                ChangelogUiState.Success(releases = releases)
            }
        }
    }

}

sealed class ChangelogUiState {

    data class Success(
        val releases: List<Release>
    ): ChangelogUiState()

    object Loading: ChangelogUiState()
}