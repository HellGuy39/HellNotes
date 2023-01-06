package com.hellguy39.hellnotes.settings

import androidx.lifecycle.ViewModel
import com.hellguy39.hellnotes.domain.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
): ViewModel() {



}