package com.hellguy39.hellnotes.feature.lock_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.DataStoreRepository
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LockSelectionViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    fun resetAppLock() {
        viewModelScope.launch {
            dataStoreRepository.saveAppLockType(LockScreenType.None)
            dataStoreRepository.saveAppCode("")
        }
    }

}
