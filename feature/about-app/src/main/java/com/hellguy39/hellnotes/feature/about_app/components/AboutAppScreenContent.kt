package com.hellguy39.hellnotes.feature.about_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.ProjectInfoProvider
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.about_app.AboutAppScreenSelection

@Composable
fun AboutAppScreenContent(
    innerPadding: PaddingValues,
    selection: AboutAppScreenSelection
) {
    val listItemModifier = Modifier.padding(16.dp)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = innerPadding
    ) {
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = UiDefaults.Elevation.Level2),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        modifier = Modifier.size(128.dp),
                        painter = painterResource(id = HellNotesIcons.DoubleStickyNote),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                        contentDescription = null
                    )
                    Column {
                        Text(
                            text = stringResource(id = HellNotesStrings.AppName),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = ProjectInfoProvider.appConfig.buildType,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = ProjectInfoProvider.appConfig.versionName,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .alpha(0.5f)
                        .padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )


                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Developed and designed by",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = "Aleksey Gadzhiev",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    OutlinedIconButton(
                        onClick = selection.onGithub
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.GitHub),
                            contentDescription = null
                        )
                    }
                }
            }
        }
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = UiDefaults.Elevation.Level2)
            ) {
                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.Changelog),
                    onClick = selection.onChangelog
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.PrivacyPolicy),
                    onClick = selection.onPrivacyPolicy
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.TermsAndConditions),
                    onClick = selection.onTermsAndConditions
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.ProvideFeedback),
                    onClick = selection.onProvideFeedback
                )

//                SelectionItem(
//                    title = stringResource(id = HellNotesStrings.MenuItem.RateOnPlayStore),
//                    onClick = selection.onRateOnPlayStore
//                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.CheckForUpdates),
                    onClick = selection.onCheckForUpdates
                )
            }
        }
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = UiDefaults.Elevation.Level2)
            ) {
                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.Reset),
                    onClick = selection.onReset
                )
            }
        }
    }
}