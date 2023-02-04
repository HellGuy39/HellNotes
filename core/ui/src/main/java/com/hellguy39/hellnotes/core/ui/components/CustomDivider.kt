package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults

@Composable
fun CustomDivider(
    isVisible: Boolean = true,
    alpha: Float = UiDefaults.Alpha.Emphasize,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.surfaceTint
) {
    AnimatedVisibility(visible = isVisible) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alpha),
            thickness = thickness,
            color = color
        )
    }
}