package com.hellguy39.hellnotes.core.ui.components.cards

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int = HellNotesIcons.Info,
    title: String = "",
    body: String = "",
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spaces.medium),
            verticalArrangement = Arrangement.spacedBy(Spaces.medium)
        ) {
            // TODO: set accented icon tint
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spaces.small)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}