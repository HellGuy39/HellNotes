package com.hellguy39.hellnotes.feature.changelog.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.hellguy39.hellnotes.core.model.repository.remote.Release
import com.hellguy39.hellnotes.core.ui.DateTimeUtils
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangelogCard(
    modifier: Modifier = Modifier,
    release: Release,
    onOpenReleaseClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier,
        onClick = { isExpanded = isExpanded.not() },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Spaces.medium),
            verticalArrangement = Arrangement.spacedBy(Spaces.medium),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(Spaces.small),
                ) {
                    Text(
                        text = release.tagName ?: "",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text =
                            DateTimeUtils.formatLocalDateTime(
                                DateTimeUtils.iso8061toLocalDateTime(
                                    release.publishedAt ?: "",
                                ),
                                DateTimeUtils.DATE_TIME_PATTERN,
                            ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
                IconButton(onClick = { isExpanded = isExpanded.not() }) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.expand(isExpanded)),
                        contentDescription = null,
                    )
                }
            }
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Spaces.medium),
                ) {
                    Divider()
                    Text(
                        text = release.body ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Divider()
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    onClick = onOpenReleaseClick,
                    contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                ) {
                    Icon(
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        painter = painterResource(id = HellNotesIcons.ArrowOutward),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = HellNotesStrings.Button.OpenARelease))
                }
            }
        }
    }
}
