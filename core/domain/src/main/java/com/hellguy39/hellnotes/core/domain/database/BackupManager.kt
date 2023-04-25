package com.hellguy39.hellnotes.core.domain.database

import android.net.Uri
import com.hellguy39.hellnotes.core.model.repository.local.file.Backup
import com.hellguy39.hellnotes.core.model.repository.local.file.Restore

interface BackupManager {

    suspend fun restoreFromBackup(filepath: Uri): Restore

    suspend fun createBackup(filepath: Uri): Backup

}