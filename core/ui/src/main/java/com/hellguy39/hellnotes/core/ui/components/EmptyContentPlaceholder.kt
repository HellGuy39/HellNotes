package com.hellguy39.hellnotes.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EmptyContentPlaceholder(
    paddingValues: PaddingValues = PaddingValues(),
    heroIcon: Painter,
    message: String,
    heroIconSize: Dp = 128.dp
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = heroIcon,
            contentDescription = null,
            modifier = Modifier
                .width(heroIconSize)
                .height(heroIconSize),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = message,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}