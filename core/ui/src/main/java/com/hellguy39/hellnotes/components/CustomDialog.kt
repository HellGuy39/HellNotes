package com.hellguy39.hellnotes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    showDialog: Boolean,
    onClose: () -> Unit,
    title: String = "",
    limitMaxHeight: Boolean = false,
    applyBottomSpace: Boolean = !limitMaxHeight,
    titleContent: @Composable () -> Unit = {  },
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    if (!showDialog)
        return

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = if (limitMaxHeight) 384.dp else Dp.Unspecified)
            ) {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleLarge
                            )
                            titleContent()
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { onClose() }
                            ) {
                                Icon(
                                    painter = painterResource(id = HellNotesIcons.Close),
                                    contentDescription = stringResource(id = HellNotesStrings.ContentDescription.Close)
                                )
                            }
                        },
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.5f),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceTint//LocalContentColor.current
                    )

                    content(PaddingValues(0.dp))

                    if (applyBottomSpace) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}