package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults

@Composable
fun CustomDivider(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    isVisible: Boolean = true,
    alpha: Float = UiDefaults.Alpha.Emphasize,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.surfaceTint
) {
    val transition = updateTransition(isVisible, label = "")

    val animatedColor by transition.animateColor(
        transitionSpec = {
            when {
                isVisible ->
                    tween(durationMillis = 300)//snap(delayMillis = 100)
                else ->
                    tween(durationMillis = 300)
            }
        }, label = ""
    ) { state ->
        when (state) {
            true -> color
            false -> Color.Transparent
        }
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .alpha(alpha),
        thickness = thickness,
        color = animatedColor
    )

}

@Composable
fun CustomVerticalDivider(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    isVisible: Boolean = true,
    alpha: Float = UiDefaults.Alpha.Emphasize,
    thickness: Dp = 1.dp,
    height: Dp = 48.dp,
    color: Color = MaterialTheme.colorScheme.surfaceTint
) {
    val transition = updateTransition(isVisible, label = "")

    val animatedColor by transition.animateColor(
        transitionSpec = {
            when {
                isVisible ->
                    tween(durationMillis = 300)//snap(delayMillis = 100)
                else ->
                    tween(durationMillis = 300)
            }
        }, label = ""
    ) { state ->
        when (state) {
            true -> color
            false -> Color.Transparent
        }
    }

    Divider(
        modifier = Modifier
            .height(height)
            .width(thickness)
            .padding(paddingValues)
            .alpha(alpha),
        thickness = thickness,
        color = animatedColor
    )

}