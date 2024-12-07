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
package com.hellguy39.hellnotes.feature.settings.screen.backup

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
internal fun BackupRoute(
    backupViewModel: BackupViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    TrackScreenView(screenName = "BackupScreen")

    BackHandler { navigateBack() }

    val createBackupLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.CreateDocument("application/octet-stream"),
        ) { uri ->
            if (uri != null) {
                backupViewModel.send(BackupUiEvent.Backup(uri))
            }
        }

    val restoreFromBackupLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocument(),
        ) { uri ->
            if (uri != null) {
                backupViewModel.send(BackupUiEvent.Restore(uri))
            }
        }

    val uiState by backupViewModel.uiState.collectAsStateWithLifecycle()

    BackupScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onBackupClick = {
            val time =
                DateTimeUtils.formatEpochMillis(
                    System.currentTimeMillis(),
                    DateTimeUtils.NEW_FILE_PATTERN,
                )
            createBackupLauncher.launch("HellNotes_Backup_$time")
        },
        onRestoreClick = {
            restoreFromBackupLauncher.launch(arrayOf("application/octet-stream"))
        },
    )
}
