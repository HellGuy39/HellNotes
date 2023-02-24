package com.hellguy39.hellnotes.core.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun SelectionIconItem(
    heroIcon: Painter? = null,
    title: String = "",
    onClick: () -> Unit = {},
    colorize: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
       if (heroIcon == null) {
           Spacer(
               modifier = Modifier.size(56.dp)
                   .padding(horizontal = 16.dp, vertical = 16.dp)
           )
       } else {
           val iconTint = if (colorize)
               MaterialTheme.colorScheme.primary
           else
               LocalContentColor.current

           Icon(
               modifier = Modifier.size(56.dp)
                   .padding(horizontal = 16.dp, vertical = 16.dp),
               painter = heroIcon,
               tint = iconTint,
               contentDescription = null
           )
       }

       val textStyle = if (colorize)
           MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
       else
           MaterialTheme.typography.bodyLarge

       Text(
           modifier = Modifier,
           text = title,
           style = textStyle
       )
    }
}