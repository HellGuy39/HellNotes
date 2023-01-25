package com.hellguy39.hellnotes.feature.home.trash.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownItem
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenu
import com.hellguy39.hellnotes.core.ui.components.CustomDropdownMenuState
import com.hellguy39.hellnotes.core.ui.components.rememberDropdownMenuState
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    selection: TrashTopAppBarSelection,
    trashDropdownMenuSelection: TrashDropdownMenuSelection
) {

    val trashDropdownMenuState = rememberDropdownMenuState()

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Trash",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { selection.onNavigation() }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.Menu),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    trashDropdownMenuState.show()
                }
            ) {
                Icon(
                    painter = painterResource(id = HellNotesIcons.MoreVert),
                    contentDescription = null
                )
            }

            TrashDropdownMenu(
                state = trashDropdownMenuState,
                selection = trashDropdownMenuSelection
            )

        }
    )
}

data class TrashTopAppBarSelection(
    val onNavigation: () -> Unit
)

@Composable
fun TrashDropdownMenu(
    state: CustomDropdownMenuState,
    selection: TrashDropdownMenuSelection,
) {
    CustomDropdownMenu(
        expanded = state.visible,
        onDismissRequest = { state.dismiss() }
    ) {
        CustomDropdownItem(
            text = "Empty trash",
            onClick = {
                state.dismiss()
                selection.onEmptyTrash()
            },
            leadingIconId = painterResource(id = HellNotesIcons.Delete)
        )
    }
}

data class TrashDropdownMenuSelection(
    val onEmptyTrash: () -> Unit,
)