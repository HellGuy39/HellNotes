package com.hellguy39.hellnotes.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.local.DataStoreRepository
import com.hellguy39.hellnotes.core.model.local.datastore.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
): ViewModel() {

    val appearanceState = combine(
            dataStoreRepository.readThemeState(),
            dataStoreRepository.readMaterialYouState()
        ) { theme, isMaterialYouEnabled ->
            AppearanceState(
                theme = theme,
                isMaterialYouEnabled = isMaterialYouEnabled
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppearanceState()
        )

}

data class AppearanceState(
    val theme: ThemeState = ThemeState.System,
    val isMaterialYouEnabled: Boolean = true
)