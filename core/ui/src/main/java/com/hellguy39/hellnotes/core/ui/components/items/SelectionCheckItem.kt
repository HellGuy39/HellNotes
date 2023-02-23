package com.hellguy39.hellnotes.core.ui.components.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun SelectionCheckItem(
    heroIcon: Painter? = null,
    title: String = "",
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (heroIcon == null) {
            Spacer(
                modifier = Modifier
                    .size(56.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(56.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                painter = heroIcon,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier,
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.weight(1f))

        Checkbox(
            modifier = Modifier.padding(horizontal = 8.dp),
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}