package com.hellguy39.hellnotes.feature.home.edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.component.items.HNListItem
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.text.UiText
import com.hellguy39.hellnotes.feature.home.edit.NoteDetailUiState
import com.hellguy39.hellnotes.feature.home.util.BottomSheetMenuItemSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentBottomSheet(
    uiState: NoteDetailUiState,
    onDismiss: () -> Unit = {},
    selection: AttachmentBottomSheetSelection = AttachmentBottomSheetSelection()
) {

    if (!uiState.isAttachmentBottomSheetOpen) {
        return
    }

    val scope = rememberCoroutineScope()
    val menuSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val items = rememberAttachmentSheetItems(
        onCloseBottomSheet = {
            scope.launch {
                menuSheetState.hide()
            }.invokeOnCompletion {
                if (!menuSheetState.isVisible) {
                    onDismiss()
                }
            }
        },
        selection = selection
    )

    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = onDismiss,
        sheetState = menuSheetState,
    ) {
        items.forEach { item ->
            HNListItem(
                modifier = Modifier.fillMaxWidth(),
                title = item.title.asString(),
                iconSize = 24.dp,
                heroIcon = painterResource(id = item.iconId),
                onClick = item.onClick
            )
        }
    }
}

data class AttachmentBottomSheetSelection(
    val onTakeAPhoto: () -> Unit = {},
    val onAddImage: () -> Unit = {},
    val onAddRecording: () -> Unit = {},
    val onAddPlace: () -> Unit = {},
    val onAddChecklist: () -> Unit = {},
    val onAddLabel: () -> Unit = {}
)

@Composable
fun rememberAttachmentSheetItems(
    onCloseBottomSheet: () -> Unit,
    selection: AttachmentBottomSheetSelection
): List<BottomSheetMenuItemSelection> {
    return remember {
        listOf(
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.TakeAPhoto),
                iconId = HellNotesIcons.PhotoCamera,
                onClick = {
                    onCloseBottomSheet()
                    selection.onTakeAPhoto()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Image),
                iconId = HellNotesIcons.Image,
                onClick = {
                    onCloseBottomSheet()
                    selection.onAddImage()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Recording),
                iconId = HellNotesIcons.Mic,
                onClick = {
                    onCloseBottomSheet()
                    selection.onAddRecording()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Place),
                iconId = HellNotesIcons.PinDrop,
                onClick = {
                    onCloseBottomSheet()
                    selection.onAddPlace()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Checklist),
                iconId = HellNotesIcons.Checklist,
                onClick = {
                    onCloseBottomSheet()
                    selection.onAddChecklist()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Labels),
                iconId = HellNotesIcons.Label,
                onClick = {
                    onCloseBottomSheet()
                    selection.onAddLabel()
                }
            )
        )
    }
}