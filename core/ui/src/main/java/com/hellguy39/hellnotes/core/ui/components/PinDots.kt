package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PinDots(pin: String) {

    val count = pin.length
    val isFirstEntered = count >= 1
    val isSecondEntered = count >= 2
    val isThirdEntered = count >= 3
    val isFourthEntered = count >= 4

    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isFirstEntered)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isSecondEntered)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isThirdEntered)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isFourthEntered)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
        )
    }
}