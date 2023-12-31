package com.hellguy39.hellnotes.feature.lockselection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.feature.lockselection.util.LockScreenTypeItemHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockSelectionScreen(
    onNavigationBack: () -> Unit,
    uiState: LockSelectionUiState,
    onLockScreenTypeSelected: (LockScreenType) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    BackHandler { onNavigationBack }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    val screenTypes =
        listOf(
            LockScreenTypeItemHolder(
                title = stringResource(id = AppStrings.MenuItem.None),
                icon = painterResource(id = AppIcons.LockOpen),
                lockScreenType = LockScreenType.None,
            ),
            LockScreenTypeItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Pin),
                icon = painterResource(id = AppIcons.Pin),
                lockScreenType = LockScreenType.Pin,
            ),
            LockScreenTypeItemHolder(
                title = stringResource(id = AppStrings.MenuItem.Password),
                icon = painterResource(id = AppIcons.Password),
                lockScreenType = LockScreenType.Password,
            ),
//        LockScreenTypeItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Pattern),
//            icon = painterResource(id = HellNotesIcons.Pattern),
//            lockScreenType = LockScreenType.Pattern,
//        ),
//        LockScreenTypeItemHolder(
//            title = stringResource(id = HellNotesStrings.MenuItem.Slide),
//            icon = painterResource(id = HellNotesIcons.SwipeRight),
//            lockScreenType = LockScreenType.Slide,
//        ),
        )

    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier,
                contentPadding = paddingValues,
            ) {
                items(screenTypes) { item ->
                    HNListItem(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                        title = item.title,
                        subtitle =
                            if (uiState.securityState.lockType == item.lockScreenType) {
                                stringResource(id = AppStrings.Subtitle.CurrentLockScreen)
                            } else {
                                ""
                            },
                        heroIcon = item.icon,
                        onClick = { onLockScreenTypeSelected(item.lockScreenType) },
                    )
                }
            }
        },
        topBar = {
            HNLargeTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationBack,
                title = stringResource(id = AppStrings.Title.ChooseANewLockScreen),
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    )
}
