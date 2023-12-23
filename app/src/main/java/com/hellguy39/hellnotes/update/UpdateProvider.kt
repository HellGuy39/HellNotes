package com.hellguy39.hellnotes.update

abstract class UpdateProvider {
    abstract suspend fun isUpdateAvailable(): Boolean

    abstract fun launchUpdate()

    abstract val providerName: String
}
