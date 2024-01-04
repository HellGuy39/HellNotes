package com.hellguy39.hellnotes.feature.home.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.feature.home.util.DrawerItem

@Composable
fun DrawerSheetContent(
    currentDestination: NavDestination?,
    drawerItems: List<DrawerItem>,
    labelItems: List<DrawerItem>,
    onManageLabelsClick: () -> Unit,
    onCreateNewLabelClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 8.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                    text =
                        buildAnnotatedString {
                            append("Hell")
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                            ) {
                                append("Notes")
                            }
                        },
                    style = MaterialTheme.typography.headlineSmall,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            drawerItems.forEach { item ->
                CustomNavDrawerItem(
                    drawerItem = item,
                    currentDestination = currentDestination,
                )
            }

            Divider(
                modifier =
                    Modifier
                        .alpha(0.5f)
                        .padding(top = 16.dp)
                        .padding(horizontal = 32.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = AppStrings.Label.Labels),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            labelItems.forEach { item ->
                CustomNavDrawerItem(
                    drawerItem = item,
                    currentDestination = currentDestination,
                )
            }

            CustomNavDrawerItem(
                drawerItem =
                    DrawerItem(
                        title = UiText.StringResources(AppStrings.MenuItem.ManageLabels),
                        icon = UiIcon.DrawableResources(AppIcons.Settings),
                        onClick = { onManageLabelsClick() },
                    ),
                currentDestination = currentDestination,
            )

            CustomNavDrawerItem(
                drawerItem =
                    DrawerItem(
                        title = UiText.StringResources(AppStrings.MenuItem.CreateNewLabel),
                        icon = UiIcon.DrawableResources(AppIcons.Add),
                        onClick = { onCreateNewLabelClick() },
                    ),
                currentDestination = currentDestination,
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                FilledTonalIconButton(onClick = { onAboutClick() }) {
                    Icon(
                        painter = painterResource(id = AppIcons.Info),
                        contentDescription = null,
                    )
                }
                FilledIconButton(onClick = { onSettingsClick() }) {
                    Icon(
                        painter = painterResource(id = AppIcons.Settings),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun CustomNavDrawerItem(
    drawerItem: DrawerItem,
    currentDestination: NavDestination?,
) {
    val isSelected =
        currentDestination?.hierarchy
            ?.any {
                it.route == drawerItem.route ||
                    if ((it.route ?: "").contains("label")) {
                        (it.route ?: "").substringBefore("/") ==
                            drawerItem.route.substringBefore("/")
                    } else {
                        false
                    }
            } ?: false

    NavigationDrawerItem(
        icon = {
            Icon(
                painter = drawerItem.icon.asPainter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        },
//        badge = {
//            if (isSelected) {
//                Text(
//                    text = "24",
//                )
//            }
//        },
        label = {
            Text(
                text = drawerItem.title.asString(),
                style = MaterialTheme.typography.labelLarge,
            )
        },
        selected = isSelected,
        onClick = { drawerItem.onClick(drawerItem) },
        modifier =
            Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding),
    )
}
