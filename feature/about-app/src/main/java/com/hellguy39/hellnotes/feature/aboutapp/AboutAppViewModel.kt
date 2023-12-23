package com.hellguy39.hellnotes.feature.aboutapp

import androidx.lifecycle.ViewModel
import com.hellguy39.hellnotes.core.domain.manager.UpdateManager
import javax.inject.Inject

class AboutAppViewModel
    @Inject
    constructor(
        private val updateManager: UpdateManager,
    ) : ViewModel() {
        init {
        }
    }
