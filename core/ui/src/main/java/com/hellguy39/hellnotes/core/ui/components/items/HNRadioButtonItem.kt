package com.hellguy39.hellnotes.core.ui.components.items

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme

@Composable
fun HNRadioButtonItem(
    modifier: Modifier = Modifier,
    title: String = "",
    isSelected: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .selectable(
                enabled = enabled,
                selected = isSelected,
                role = Role.RadioButton,
                onClick = onClick
            )
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = null,
                enabled = enabled
            )
            Text(
                modifier = Modifier
                    .alpha(if (enabled) 1f else UiDefaults.Alpha.Hint),
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(name = "LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HNRadioButtonItemPreview() {
    HellNotesTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            HNRadioButtonItem(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                title = "Title"
            )
        }
    }
}
