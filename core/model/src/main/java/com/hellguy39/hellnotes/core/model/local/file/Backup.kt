package com.hellguy39.hellnotes.core.model.local.file

data class Backup(
    val uri: String,
) {
    fun isInitialInstance() = this == initialInstance()

    companion object {
        fun initialInstance() = Backup(
            uri = ""
        )
    }
}
