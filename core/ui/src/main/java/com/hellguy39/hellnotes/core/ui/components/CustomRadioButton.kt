package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults

@Composable
fun CustomRadioButton(
    modifier: Modifier = Modifier,
    title: String = "",
    isSelected: Boolean = false,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        RadioButton(
            selected = isSelected,
            onClick = null,
            enabled = enabled
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier
                .alpha(if (enabled) 1f else UiDefaults.Alpha.Hint),
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}