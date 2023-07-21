package com.hellguy39.hellnotes.feature.settings

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.hellguy39.hellnotes.core.ui.component.navigation.HNNavigationItemSelection
import com.hellguy39.hellnotes.core.ui.layout.ListDetail
import com.hellguy39.hellnotes.core.ui.model.GraphScreen
import com.hellguy39.hellnotes.core.ui.model.HNContentType
import com.hellguy39.hellnotes.core.ui.value.spacing
import com.hellguy39.hellnotes.feature.settings.detail.general.generalScreen
import com.hellguy39.hellnotes.feature.settings.detail.security.securityScreen
import com.hellguy39.hellnotes.feature.settings.detail.theme.themeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SettingsRoute(
    settingsViewModel: SettingsViewModel,
    settingsNavController: NavHostController,
    contentType: HNContentType,
    displayFeatures: List<DisplayFeature>,
    navItems: List<HNNavigationItemSelection>,
    currentDestination: NavDestination?
) {
    var isDetailOpen by rememberSaveable { mutableStateOf(false) }

    ListDetail(
        modifier = Modifier,
        isDetailOpen = isDetailOpen,
        onCloseDetail = { isDetailOpen = false },
        contentType = contentType,
        detailKey = currentDestination?.route,
        list = { isDetailVisible ->
            Scaffold(
                content = { innerPadding ->
                    LazyColumn(
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.spacing.medium,
                            vertical = MaterialTheme.spacing.medium
                        ),
                        contentPadding = innerPadding
                    ) {
                        items(navItems) { navItem ->
                            CardSettingsNavigation(
                                modifier = Modifier.fillMaxWidth(),
                                selection = navItem,
                                currentDestination = currentDestination,
                                onClick = {
                                    isDetailOpen = true
                                    navItem.onClick(navItem)
                                }
                            )
                        }
                    }
                }
            )
        },
        detail = { isListVisible ->
            AnimatedNavHost(
                modifier = Modifier,
                navController = settingsNavController,
                startDestination = GraphScreen.Settings.start().route
            ) {
                generalScreen(settingsViewModel)

                securityScreen(settingsViewModel)

                themeScreen(settingsViewModel)
            }
        },
        twoPaneStrategy = HorizontalTwoPaneStrategy(splitFraction = 1f / 3f, gapWidth = 0.dp),
        displayFeatures = displayFeatures
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
            selection.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = selection.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = selection.subtitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}