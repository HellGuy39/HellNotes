package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HNIconButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    enabledPainter: Painter,
    disabledPainter: Painter? = null,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp
) {
    if (enabled) {
        IconButton(
            modifier = Modifier.size(containerSize),
            onClick = onClick
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = enabledPainter,
                contentDescription = null
            )
        }
    } else {
        Box(
            modifier = Modifier.size(containerSize),
            contentAlignment = Alignment.Center
        ) {
            if (disabledPainter == null) {
                Spacer(
                    modifier = Modifier.size(iconSize)
                )
            } else {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = disabledPainter,
                    contentDescription = null
                )
            }
        }
    }
}