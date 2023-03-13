package com.hellguy39.hellnotes.core.ui.components.cards

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.CustomVerticalDivider
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme

@Composable
fun TipCard(
    isVisible: Boolean = true,
    title: String = "",
    message: String = "",
    onClose: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent//MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Row(
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    if (title.isNotEmpty()) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1
                        )
                    }
                    if (message.isNotEmpty()) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2
                        )
                    }
                }

                CustomVerticalDivider(height = 48.dp)

                Spacer(modifier = Modifier.padding(end = 12.dp))

                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = onClose,
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Close),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview(name = "LightMode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "DarkMode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TipCardPreview() {
    HellNotesTheme {
        TipCard(
            title = "Easy Care",
            message = "This plant is appropriate for beginners. " +
                    "This plant is appropriate for beginners"
        )
    }
}