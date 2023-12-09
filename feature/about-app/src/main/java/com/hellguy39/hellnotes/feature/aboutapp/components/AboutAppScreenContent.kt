package com.hellguy39.hellnotes.feature.aboutapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.ui.components.items.HNListItem
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.aboutapp.AboutAppScreenSelection

@Composable
fun AboutAppScreenContent(
    innerPadding: PaddingValues,
    selection: AboutAppScreenSelection,
) {
    val listItemModifier = Modifier.padding(Spaces.medium)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spaces.medium),
        contentPadding =
            PaddingValues(
                top = innerPadding.calculateTopPadding() + Spaces.medium,
                bottom = innerPadding.calculateBottomPadding() + Spaces.medium,
            ),
    ) {
        item {
            Text(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
                text = "Version: ${ProjectInfoProvider.appConfig.versionName}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
            )
        }
        item {
            ElevatedCard(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
            ) {
                Row(
                    modifier = Modifier.padding(Spaces.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spaces.medium),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Spaces.extraSmall),
                    ) {
                        Text(
                            text = "Developed and designed by",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = "Aleksey Gadzhiev",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    OutlinedIconButton(
                        onClick = selection.onGithub,
                    ) {
                        Icon(
                            painter = painterResource(id = HellNotesIcons.GitHub),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = Spaces.medium),
            ) {
                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.Changelog),
                    onClick = selection.onChangelog,
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.PrivacyPolicy),
                    onClick = selection.onPrivacyPolicy,
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.TermsAndConditions),
                    onClick = selection.onTermsAndConditions,
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.ProvideFeedback),
                    onClick = selection.onProvideFeedback,
                )

                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.CheckForUpdates),
                    onClick = selection.onCheckForUpdates,
                )
            }
        }
        item {
            ElevatedCard(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spaces.medium),
            ) {
                HNListItem(
                    modifier = listItemModifier,
                    title = stringResource(id = HellNotesStrings.MenuItem.Reset),
                    onClick = selection.onReset,
                )
            }
        }
    }
}
