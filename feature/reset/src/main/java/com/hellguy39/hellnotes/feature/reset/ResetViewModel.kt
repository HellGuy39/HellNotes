package com.hellguy39.hellnotes.feature.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.use_case.ResetAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(
    private val resetAppUseCase: ResetAppUseCase
): ViewModel() {

    fun reset(resetDatabase: Boolean, resetSettings: Boolean) {
        viewModelScope.launch {
            resetAppUseCase.invoke(resetDatabase, resetSettings)
        }
    }

}