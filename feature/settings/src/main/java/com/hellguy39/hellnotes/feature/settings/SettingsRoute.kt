package com.hellguy39.hellnotes.feature.settings

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.component.top_bar.HNAdaptiveTopAppBar
import com.hellguy39.hellnotes.core.ui.layout.ListDetail
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.resource.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.value.LocalSpacing
import com.hellguy39.hellnotes.core.ui.value.spacing
import com.hellguy39.hellnotes.core.ui.window.isCompact
import com.hellguy39.hellnotes.feature.settings.detail.general.GeneralRoute
import com.hellguy39.hellnotes.feature.settings.detail.security.SecurityRoute
import com.hellguy39.hellnotes.feature.settings.detail.theme.AppearanceRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel,
    settingsNavController: NavHostController,
    windowWidthSizeClass: WindowWidthSizeClass,
    contentType: HNContentType,
    displayFeatures: List<DisplayFeature>,
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?,
    onBackNavigation: () -> Unit
) {
    var isDetailOpen by rememberSaveable { mutableStateOf(false) }
    val spacing = LocalSpacing.current
    val selectedSectionsRoute by settingsViewModel.selectedSettingsSection.collectAsStateWithLifecycle()

    val horizontalListSpacing by remember {
        mutableStateOf(
            if (windowWidthSizeClass.isCompact()) spacing.medium else spacing.none
        )
    }

    ListDetail(
        modifier = Modifier.fillMaxSize(),
        isDetailOpen = selectedSectionsRoute != null,
        onCloseDetail = { settingsViewModel.setSection(null) },
        contentType = contentType,
        detailKey = currentDestination?.route,
        list = { isDetailVisible ->

            val appBarState = rememberTopAppBarState()
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    HNAdaptiveTopAppBar(
                        windowWidthSize = windowWidthSizeClass,
                        content = {
                            Text(
                                text = stringResource(id = HellNotesStrings.Title.Settings),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackNavigation) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.ArrowBack),
                                    contentDescription = null
                                )
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(
                            horizontal = horizontalListSpacing,
                            //vertical = MaterialTheme.spacing.medium
                        ),
                        contentPadding = innerPadding
                    ) {
                        items(navItems) { navItem ->
                            CardSettingsNavigation(
                                modifier = Modifier.fillMaxWidth(),
                                selection = navItem,
                                currentDestination = currentDestination,
                                onClick = {
                                    settingsViewModel.setSection(navItem.screen.route)
                                    //isDetailOpen = true
                                    //navItem.onClick(navItem)
                                }
                            )
                        }
                    }
                }
            )
        },
        detail = { isListVisible ->
            when(selectedSectionsRoute) {
                GraphScreen.Settings.General.route -> {
                    GeneralRoute(settingsViewModel)
                }
                GraphScreen.Settings.Security.route -> {
                    SecurityRoute(settingsViewModel)
                }
                GraphScreen.Settings.Appearance.route -> {
                    AppearanceRoute()
                }
                else -> Unit
            }
        },
        twoPaneStrategy = HorizontalTwoPaneStrategy(splitFraction = 1 / 3f, gapWidth = 0.dp),
        displayFeatures = calculateDisplayFeatures(activity = LocalContext.current as Activity)
    )
}

@Composable
fun CardSettingsNavigation(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selection: HNNavigationItemSelection,
    currentDestination: NavDestination?
) {
    val isSelected = currentDestination?.hierarchy
        ?.any { navDestination -> navDestination.route == selection.screen.route } ?: false
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent

    Box(
        modifier = modifier
            .then(
                Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(color = backgroundColor)
                    .clickable(
                        enabled = true,
                        onClick = onClick
                    )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            selection.iconId?.let { iconId ->
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = selection.title.asString(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = selection.subtitle.asString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}