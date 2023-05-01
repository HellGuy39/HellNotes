package com.hellguy39.hellnotes.core.ui.components.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun HNSwitchItem(
    modifier: Modifier = Modifier,
    title: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onClick: () -> Unit = {},
    thumbCheckedIcon: Painter? = null,
    thumbUncheckedIcon: Painter? = null
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
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = checked,
                onCheckedChange = null,
                enabled = enabled,
                thumbContent = if (checked && thumbCheckedIcon != null) {
                    {
                        Icon(
                            painter = thumbCheckedIcon,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else if (thumbUncheckedIcon != null) {
                    {
                        Icon(
                            painter = thumbUncheckedIcon,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else null
            )
        }
    }
}