package com.hellguy39.hellnotes.core.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellguy39.hellnotes.core.domain.repository.settings.SettingsRepository
import com.hellguy39.hellnotes.core.model.ColorMode
import com.hellguy39.hellnotes.core.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppThemeViewModel
@Inject
constructor(
    settingsRepository: SettingsRepository
): ViewModel() {

    val uiState = settingsRepository.getAppearanceStateFlow()
        .map { appearanceState ->
            AppThemeUiState(
                theme = appearanceState.theme,
                colorMode = appearanceState.colorMode
            )
        }
        .stateIn(
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppThemeUiState(),
            scope = viewModelScope
        )

}

data class AppThemeUiState(
    val theme: Theme = Theme.defaultValue(),
    val colorMode: ColorMode = ColorMode.defaultValue()
)
