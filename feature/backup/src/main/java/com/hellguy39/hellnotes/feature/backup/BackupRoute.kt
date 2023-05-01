package com.hellguy39.hellnotes.feature.backup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun BackupRoute(
    navController: NavController,
    backupViewModel: BackupViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    val createBackupLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/octet-stream")) { uri ->
        if (uri != null) {
            backupViewModel.send(BackupUiEvent.Backup(uri))
        }
    }

    val restoreFromBackupLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            backupViewModel.send(BackupUiEvent.Restore(uri))
        }
    }

    val uiState by backupViewModel.uiState.collectAsStateWithLifecycle()

    BackupScreen(
        onNavigationButtonClick = navController::popBackStack,
        uiState = uiState,
        selection = BackupScreenSelection(
            onBackup = {
                val time = DateTimeUtils.formatEpochMillis(
                    System.currentTimeMillis(),
                    DateTimeUtils.NEW_FILE_PATTERN
                )
                createBackupLauncher.launch("HellNotes_Backup_$time")
            },
            onRestore = {
                restoreFromBackupLauncher.launch(arrayOf("application/octet-stream"))
            }
        )
    )

}