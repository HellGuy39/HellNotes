package com.hellguy39.hellnotes.core.ui.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HNFilledIconButton(
    onClick: () -> Unit,
    enabledPainter: Painter,
    containerSize: Dp = 48.dp,
    iconSize: Dp = 24.dp
) {
    FilledIconButton(
        modifier = Modifier.size(containerSize),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = enabledPainter,
            contentDescription = null
        )
    }
}