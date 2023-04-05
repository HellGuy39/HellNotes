package com.hellguy39.hellnotes.feature.checklist_edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.system.BackHandler

@Composable
fun ChecklistEditRoute(
    navController: NavController,
    checklistEditViewModel: ChecklistEditViewModel = hiltViewModel()
) {
    BackHandler(onBack = navController::popBackStack)

    val checklist by checklistEditViewModel.checklist.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    val currentOnStop by rememberUpdatedState {
        checklistEditViewModel.send(ChecklistEditUiEvent.OnClose)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_STOP -> currentOnStop()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ChecklistEditScreen(
        onNavigation = navController::popBackStack,
        checklist = checklist,
        selection = ChecklistEditScreenSelection(
            onMove = { from, to ->
                checklistEditViewModel.send(ChecklistEditUiEvent.OnMoveItem(from, to))
            },
            onTextChange = { text, item ->
                checklistEditViewModel.send(
                    ChecklistEditUiEvent.OnUpdateItemText(text = text, item = item)
                )
            },
            onDelete = { item ->
                checklistEditViewModel.send(ChecklistEditUiEvent.OnRemoveItem(item))
            },
            onAdd = {
                checklistEditViewModel.send(ChecklistEditUiEvent.OnAddItem)
            }
        )
    )
}