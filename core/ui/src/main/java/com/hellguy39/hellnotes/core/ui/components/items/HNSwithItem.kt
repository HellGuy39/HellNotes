package com.hellguy39.hellnotes.core.ui.components.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun HNSwitchItem(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onClick: () -> Unit = {},
    showDivider: Boolean = true
) {
    Box(
        modifier = Modifier
            .selectable(
                role = Role.Switch,
                onClick = onClick,
                selected = checked
            )
    ) {
        Row(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ),
            horizontalArrangement = Arrangement.spacedBy(Spaces.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spaces.small)
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (showDivider) {
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(DividerDefaults.Thickness)
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = null,
                enabled = enabled
            )
        }
    }
}