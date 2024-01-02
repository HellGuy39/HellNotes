package com.hellguy39.hellnotes.core.ui.components.placeholer

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
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.values.Spaces

@Composable
fun EmptyContentPlaceholder(
    modifier: Modifier = Modifier,
    heroIcon: Painter,
    message: String,
    heroIconSize: Dp = 128.dp,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = heroIcon,
            contentDescription = null,
            modifier =
                Modifier
                    .size(heroIconSize),
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            textAlign = TextAlign.Center,
        )
        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        }
    }
}

@Composable
fun EmptyContentPlaceholder(
    modifier: Modifier = Modifier,
    heroIcon: UiIcon,
    message: UiText,
    heroIconSize: Dp = 128.dp,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    Column(
        modifier =
            modifier.then(
                Modifier.padding(horizontal = Spaces.extraLarge),
            ),
        verticalArrangement =
            Arrangement.spacedBy(
                space = Spaces.medium,
                alignment = Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = heroIcon.asPainter(),
            contentDescription = null,
            modifier =
                Modifier
                    .size(heroIconSize),
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = message.asString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            textAlign = TextAlign.Center,
        )
        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                actions()
            }
        }
    }
}
