package com.hellguy39.hellnotes.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.LabelRepository
import com.hellguy39.hellnotes.core.model.repository.local.database.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        labelRepository: LabelRepository,
    ) : ViewModel() {
        val uiState: StateFlow<HomeUiState> =
            labelRepository.getAllLabelsStream()
                .map { labels -> HomeUiState(labels = labels, isIdle = false) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = HomeUiState(),
                )
    }

data class HomeUiState(
    val labels: List<Label> = listOf(),
    val isIdle: Boolean = true,
)
