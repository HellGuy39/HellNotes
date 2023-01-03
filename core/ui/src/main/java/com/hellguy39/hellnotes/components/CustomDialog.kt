package com.hellguy39.hellnotes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    showDialog: Boolean,
    onClose: () -> Unit,
    title: String = "",
    titleContent: @Composable () -> Unit = {  },
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    if (!showDialog)
        return

    Dialog(onDismissRequest = onClose) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 384.dp)
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
                }
            }
        }
    }
}