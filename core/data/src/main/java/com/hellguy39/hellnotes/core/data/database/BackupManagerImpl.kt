package com.hellguy39.hellnotes.core.data.database

import android.content.Context
import android.net.Uri
import com.hellguy39.hellnotes.core.database.HellNotesDatabase
import com.hellguy39.hellnotes.core.domain.manager.BackupManager
import com.hellguy39.hellnotes.core.model.repository.local.file.Backup
import com.hellguy39.hellnotes.core.model.repository.local.file.Restore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class BackupManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : BackupManager {

    override suspend fun restoreFromBackup(filepath: Uri): Restore {
        val contentResolver = context.contentResolver
        val database = HellNotesDatabase.getDatabase(context)

        val databaseFile = File(database.openHelper.writableDatabase.path.toString())

        contentResolver.openInputStream(filepath)?.use { stream ->
            databaseFile.writeBytes(stream.readBytes())
        }

        return Restore(uri = filepath.toString())
    }

    override suspend fun createBackup(filepath: Uri): Backup {
        val contentResolver = context.contentResolver
        val database = HellNotesDatabase.getDatabase(context)

        val databaseFile = File(database.openHelper.writableDatabase.path.toString())

        contentResolver.openOutputStream(filepath)?.use { stream ->
            stream.write(databaseFile.readBytes())
        }

        return Backup(uri = filepath.toString())
    }

}