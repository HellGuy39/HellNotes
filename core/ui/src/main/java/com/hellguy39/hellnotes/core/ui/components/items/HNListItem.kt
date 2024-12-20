/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.core.ui.components.items

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiIcon
import com.hellguy39.hellnotes.core.ui.resources.wrapper.UiText
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.core.ui.values.Alpha

@Composable
fun HNListItem(
    modifier: Modifier = Modifier,
    heroIcon: UiIcon = UiIcon.Empty,
    title: UiText = UiText.Empty,
    subtitle: UiText = UiText.Empty,
    onClick: () -> Unit = {},
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconSize: Dp = 32.dp,
) {
    Box(
        modifier = Modifier.clickable { onClick() },
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
        ) {
            if (heroIcon !is UiIcon.Empty) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = heroIcon.asPainter(),
                    tint = iconTint,
                    contentDescription = null,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (title !is UiText.Empty) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title.asString(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start,
                    )
                }
                if (subtitle !is UiText.Empty) {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .alpha(Alpha.LEVEL_1),
                        text = subtitle.asString(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Composable
fun HNListItem(
    modifier: Modifier = Modifier,
    heroIcon: Painter? = null,
    title: String = "",
    subtitle: String = "",
    onClick: () -> Unit = {},
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconSize: Dp = 32.dp,
) {
    Box(
        modifier = Modifier.clickable { onClick() },
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterHorizontally),
        ) {
            if (heroIcon != null) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = heroIcon,
                    tint = iconTint,
                    contentDescription = null,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start,
                    )
                }
                if (subtitle.isNotEmpty()) {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .alpha(Alpha.LEVEL_1),
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

@Preview(name = "Title & Subtitle with Icon / LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Title & Subtitle with Icon / DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HNListItemPreviewTitleAndSubtitleWithIcon() {
    HellNotesTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background,
        ) {
            HNListItem(
                modifier = Modifier.padding(16.dp),
                heroIcon = painterResource(id = AppIcons.Share),
                title = "Title",
                subtitle = "Subtitle",
            )
        }
    }
}

@Preview(name = "Title & Subtitle / LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Title & Subtitle / DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HNListItemPreviewTitleAndSubtitle() {
    HellNotesTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background,
        ) {
            HNListItem(
                modifier = Modifier.padding(16.dp),
                title = "Title",
                subtitle = "Subtitle",
            )
        }
    }
}

@Preview(name = "Title only / LightMode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Title only / DarkMode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HNListItemPreviewTitleOnly() {
    HellNotesTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background,
        ) {
            HNListItem(
                modifier = Modifier.padding(16.dp),
                title = "Title",
            )
        }
    }
}
