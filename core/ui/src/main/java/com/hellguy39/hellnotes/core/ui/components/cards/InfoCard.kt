package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: UiIcon = UiIcon.DrawableResources(AppIcons.Info),
    title: UiText = UiText.Empty,
    body: UiText = UiText.Empty,
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Spaces.medium),
            verticalArrangement = Arrangement.spacedBy(Spaces.medium),
        ) {
            Icon(
                painter = icon.asPainter(),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spaces.small),
            ) {
                Text(
                    text = title.asString(),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = body.asString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
