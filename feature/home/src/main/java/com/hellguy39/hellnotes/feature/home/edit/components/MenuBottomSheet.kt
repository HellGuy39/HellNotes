package com.hellguy39.hellnotes.feature.home.edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import com.hellguy39.hellnotes.feature.home.edit.NoteEditUiState
import com.hellguy39.hellnotes.feature.home.edit.NoteWrapperState
import com.hellguy39.hellnotes.feature.home.util.BottomSheetMenuItemSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuBottomSheet(
    uiState: NoteEditUiState,
    onDismiss: () -> Unit = {},
    selection: MenuBottomSheetSelection = MenuBottomSheetSelection()
) {

    if (!uiState.noteEditDialogState.isMenuBottomSheetOpen) {
        return
    }

    val scope = rememberCoroutineScope()
    val menuSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val items = rememberMenuBottomSheetItems(
        onCloseBottomSheet = {
            scope.launch {
                menuSheetState.hide()
            }.invokeOnCompletion {
                if (!menuSheetState.isVisible) {
                    onDismiss()
                }
            }
        },
        selection = selection,
        isArchived = if (uiState.noteWrapperState is NoteWrapperState.Success) uiState.noteWrapperState.noteWrapper.note.isArchived else false
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

data class MenuBottomSheetSelection(
    val onDelete: () -> Unit = {},
    val onMakeACopy: () -> Unit = {},
    val onArchive: () -> Unit = {},
    val onShare: () -> Unit = {},
    val onColor: () -> Unit = {}
)

@Composable
fun rememberMenuBottomSheetItems(
    onCloseBottomSheet: () -> Unit,
    selection: MenuBottomSheetSelection,
    isArchived: Boolean
): List<BottomSheetMenuItemSelection> {
    return remember {
        listOf(
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Delete),
                iconId = HellNotesIcons.Delete,
                onClick = {
                    onCloseBottomSheet()
                    selection.onDelete()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.MakeACopy),
                iconId = HellNotesIcons.ContentCopy,
                onClick = {
                    onCloseBottomSheet()
                    selection.onMakeACopy()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(
                    if (isArchived) HellNotesStrings.MenuItem.Unarchive else HellNotesStrings.MenuItem.Archive
                ),
                iconId = if (isArchived) HellNotesIcons.Unarchive else HellNotesIcons.Archive,
                onClick = {
                    onCloseBottomSheet()
                    selection.onArchive()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Share),
                iconId = HellNotesIcons.Share,
                onClick = {
                    onCloseBottomSheet()
                    selection.onShare()
                }
            ),
            BottomSheetMenuItemSelection(
                title = UiText.StringResources(HellNotesStrings.MenuItem.Color),
                iconId = HellNotesIcons.Palette,
                onClick = {
                    onCloseBottomSheet()
                    selection.onColor()
                }
            )
        )
    }
}