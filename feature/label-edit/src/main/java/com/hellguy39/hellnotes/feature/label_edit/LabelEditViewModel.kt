package com.hellguy39.hellnotes.feature.label_edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.LabelRepository
import com.hellguy39.hellnotes.core.model.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelEditViewModel @Inject constructor(
    private val labelRepository: LabelRepository
): ViewModel() {

    val labels = labelRepository.getAllLabelsStream()
        .map { labels ->
            labels.sortedByDescending { label -> label.id }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            listOf()
        )

    fun insertLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.insertLabel(label)
        }
    }

    fun deleteLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.deleteLabel(label)
        }
    }

    fun updateLabel(label: Label) {
        viewModelScope.launch {
            labelRepository.updateLabel(label)
        }
    }

}