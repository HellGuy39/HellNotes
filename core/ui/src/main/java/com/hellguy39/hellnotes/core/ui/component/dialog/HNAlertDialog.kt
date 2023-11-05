package com.hellguy39.hellnotes.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hellguy39.hellnotes.core.ui.component.divider.CustomDivider
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.value.elevation
import com.hellguy39.hellnotes.core.ui.value.spacing

@Composable
fun HNAlertDialog(
    isOpen: Boolean,
    onClose: () -> Unit = { },
    heroIcon: Painter? = null,
    title: String = "",
    message: String = "",
    onCancel: (() -> Unit)? = null,
    onAccept: (() -> Unit)? = null,
    showContentDividers: Boolean = true,
    dialogProperties: DialogProperties = DialogProperties(usePlatformDefaultWidth = true),
    content: (@Composable () -> Unit)? = null
) {
    if (!isOpen)
        return

    Dialog(
        onDismissRequest = onClose,
        properties = dialogProperties,
    ) {
        Surface(
            //modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(MaterialTheme.elevation.level3)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium, vertical = MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.spacing.medium,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    if (heroIcon != null) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = heroIcon,
                            tint = MaterialTheme.colorScheme.secondary,
                            contentDescription = null
                        )
                    }
                    if (title.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                    }
                    if (message.isNotEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start
                        )
                    }
                }
                if (content != null) {
                    Column {
                        if (showContentDividers) {
                            CustomDivider(
                                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        content()

                        if (showContentDividers) {
                            CustomDivider(
                                paddingValues = PaddingValues(horizontal = MaterialTheme.spacing.medium),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    }
                }
                if (onCancel != null || onAccept != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.medium)
                            .padding(bottom = MaterialTheme.spacing.medium),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp, alignment = Alignment.End
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (onCancel != null) {
                            TextButton(
                                onClick = onCancel,
                            ) {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Button.Cancel),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                        if (onAccept != null) {
                            Button(
                                modifier = Modifier.padding(start = 8.dp),
                                onClick = onAccept,
                            ) {
                                Text(
                                    text = stringResource(id = HellNotesStrings.Button.Accept),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}