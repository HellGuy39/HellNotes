package com.hellguy39.hellnotes.core.ui.components

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
fun CustomCheckbox(
    modifier: Modifier = Modifier,
    heroIcon: Painter? = null,
    title: String = "",
    checked: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (heroIcon == null) {
            Spacer(
                modifier = Modifier
                    .size(16.dp)
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
            modifier = Modifier.padding(horizontal = 16.dp),
            checked = checked,
            onCheckedChange = null
        )
    }
}