/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

class BackupManagerImpl
    @Inject
    constructor(
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
