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
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme

@Composable
fun HNCheckboxItem(
    modifier: Modifier = Modifier,
    heroIcon: Painter? = null,
    title: String = "",
    checked: Boolean = false,
    onClick: () -> Unit = {},
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconSize: Dp = 32.dp
) {
    Box(
        modifier = Modifier
            .selectable(
                selected = checked,
                onClick = onClick,
                role = Role.RadioButton
            ),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally)
        ) {
            if (heroIcon != null) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = heroIcon,
                    tint = iconTint,
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            Checkbox(
                modifier = Modifier,
                checked = checked,
                onCheckedChange = null
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
            color = MaterialTheme.colorScheme.background
        ) {
            HNCheckboxItem(
                modifier = Modifier.padding(16.dp),
                heroIcon = painterResource(id = HellNotesIcons.Share),
                title = "Title",
            )
        }
    }
}
