package com.hellguy39.hellnotes.update

import android.content.Context
import com.hellguy39.hellnotes.core.domain.manager.UpdateManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UpdateManagerImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : UpdateManager {
        private val updateProvider = UpdateProviderFactory.create(context)

        override val providerName: String
            get() = updateProvider.providerName

        override suspend fun checkForUpdates() {
            if (updateProvider.isUpdateAvailable()) {
                updateProvider.launchUpdate()
            }
        }
    }
