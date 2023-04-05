package com.hellguy39.hellnotes.core.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults

@Composable
fun SelectionItemDefault(
    heroIcon: Painter? = null,
    title: String = "",
    subtitle: String = "",
    onClick: () -> Unit = {},
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(UiDefaults.ListItem.DefaultHeight)
            .clickable { onClick() },
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
               tint = iconTint,
               contentDescription = null
           )
       }
       Column(
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           if (title.isNotEmpty()) {
               Text(
                   modifier = Modifier.fillMaxWidth(),
                   text = title,
                   style = MaterialTheme.typography.bodyLarge,
                   textAlign = TextAlign.Start
               )
           }
           if (subtitle.isNotEmpty()) {
               Text(
                   modifier = Modifier.fillMaxWidth().alpha(UiDefaults.Alpha.Accented),
                   text = subtitle,
                   style = MaterialTheme.typography.labelSmall,
                   textAlign = TextAlign.Start
               )
           }
       }
    }
}