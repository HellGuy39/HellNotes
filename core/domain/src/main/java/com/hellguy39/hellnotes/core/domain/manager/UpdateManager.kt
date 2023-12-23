package com.hellguy39.hellnotes.core.domain.manager

interface UpdateManager {
    suspend fun checkForUpdates()

    val providerName: String
}
