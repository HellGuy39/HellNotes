package com.hellguy39.hellnotes.core.ui.component.items

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.component.divider.HNVerticalDivider
import com.hellguy39.hellnotes.core.ui.value.Spacing
import com.hellguy39.hellnotes.core.ui.value.spacing

@Composable
fun HNSwitchItem(
    modifier: Modifier = Modifier,
    title: String = "",
    enabled: Boolean = true,
    checked: Boolean,
    onClick: () -> Unit = {},
    thumbCheckedIcon: Painter? = null,
    thumbUncheckedIcon: Painter? = null,
    innerPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.medium)
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
            modifier = Modifier.padding(innerPadding)
                .then(modifier),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                Text(
                    modifier = Modifier,
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = MaterialTheme.spacing.medium,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HNVerticalDivider(
                    modifier = Modifier.height(40.dp)
                )

                Switch(
                    checked = checked,
                    onCheckedChange = null,
                    enabled = enabled,
                    thumbContent = if (checked && thumbCheckedIcon != null) {
                        {
                            Icon(
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                painter = thumbCheckedIcon,
                                contentDescription = null,
                            )
                        }
                    } else if (thumbUncheckedIcon != null) {
                        {
                            Icon(
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                painter = thumbUncheckedIcon,
                                contentDescription = null,
                            )
                        }
                    } else null
                )
            }
        }
    }
}