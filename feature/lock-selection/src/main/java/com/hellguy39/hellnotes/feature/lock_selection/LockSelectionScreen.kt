package com.hellguy39.hellnotes.feature.lock_selection

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.core.ui.components.items.SelectionIconItem
import com.hellguy39.hellnotes.feature.lock_selection.util.LockScreenTypeItemHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockSelectionScreen(
    onNavigationBack: () -> Unit,
    onLockScreenTypeSelected: (LockScreenType) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    BackHandler(onBack = onNavigationBack)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val screenTypes = listOf(
        LockScreenTypeItemHolder(
            title = stringResource(id = HellNotesStrings.MenuItem.None),
            icon = painterResource(id = HellNotesIcons.LockOpen),
            lockScreenType = LockScreenType.None,
        ),
        LockScreenTypeItemHolder(
            title = stringResource(id = HellNotesStrings.MenuItem.Pin),
            icon = painterResource(id = HellNotesIcons.Pin),
            lockScreenType = LockScreenType.Pin,
        ),
        LockScreenTypeItemHolder(
            title = stringResource(id = HellNotesStrings.MenuItem.Password),
            icon = painterResource(id = HellNotesIcons.Password),
            lockScreenType = LockScreenType.Password,
        ),
        LockScreenTypeItemHolder(
            title = stringResource(id = HellNotesStrings.MenuItem.Pattern),
            icon = painterResource(id = HellNotesIcons.Pattern),
            lockScreenType = LockScreenType.Pattern,
        ),
        LockScreenTypeItemHolder(
            title = stringResource(id = HellNotesStrings.MenuItem.Slide),
            icon = painterResource(id = HellNotesIcons.SwipeRight),
            lockScreenType = LockScreenType.Slide,
        ),
    )

    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier,
                contentPadding = paddingValues
            ) {
                items(screenTypes) { item ->
                    SelectionIconItem(
                        title = item.title,
                        heroIcon = item.icon,
                        onClick = { onLockScreenTypeSelected(item.lockScreenType) }
                    )
                }
            }
        },
        topBar = {
            CustomLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                title = stringResource(id = HellNotesStrings.Title.ChooseANewLockScreen)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
}