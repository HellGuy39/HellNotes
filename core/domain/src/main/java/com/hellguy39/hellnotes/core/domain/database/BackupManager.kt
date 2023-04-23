package com.hellguy39.hellnotes.core.domain.database

import android.net.Uri

interface BackupManager {

    suspend fun restore(filepath: Uri)

    suspend fun backup(filepath: Uri)

}