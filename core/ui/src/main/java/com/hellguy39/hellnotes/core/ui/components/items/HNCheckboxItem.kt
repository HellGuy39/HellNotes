package com.hellguy39.hellnotes.core.ui.components.items

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun HNCheckboxItem(
    modifier: Modifier = Modifier,
    heroIcon: UiIcon = UiIcon.Empty,
    title: UiText = UiText.Empty,
    subtitle: UiText = UiText.Empty,
    checked: Boolean = false,
    onClick: () -> Unit = {},
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconSize: Dp = 32.dp,
) {
    Box(
        modifier =
            Modifier
                .selectable(
                    selected = checked,
                    onClick = onClick,
                    role = Role.RadioButton,
                ),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
        ) {
            if (heroIcon !is UiIcon.Empty) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = heroIcon.asPainter(),
                    tint = iconTint,
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spaces.small),
            ) {
                if (title !is UiText.Empty) {
                    Text(
                        text = title.asString(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                if (subtitle !is UiText.Empty) {
                    Text(
                        text = subtitle.asString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Checkbox(
                modifier = Modifier,
                checked = checked,
                onCheckedChange = null,
            )
        }
    }
}

@Composable
fun HNCheckboxItem(
    modifier: Modifier = Modifier,
    heroIcon: Painter? = null,
    title: String = "",
    subtitle: String = "",
    checked: Boolean = false,
    onClick: () -> Unit = {},
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconSize: Dp = 32.dp,
) {
    Box(
        modifier =
            Modifier
                .selectable(
                    selected = checked,
                    onClick = onClick,
                    role = Role.RadioButton,
                ),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
        ) {
            if (heroIcon != null) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = heroIcon,
                    tint = iconTint,
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spaces.small),
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Checkbox(
                modifier = Modifier,
                checked = checked,
                onCheckedChange = null,
            )
        }
    }
}

@Preview(name = "LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HNCheckboxItemPreview() {
    HellNotesTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background,
        ) {
            HNCheckboxItem(
                modifier = Modifier.padding(16.dp),
                heroIcon = painterResource(id = AppIcons.Share),
                title = "Title",
                subtitle = "subtitle",
            )
        }
    }
}
