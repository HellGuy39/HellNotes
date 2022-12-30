package com.hellguy39.hellnotes.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hellguy39.hellnotes.ui.HellNotesIcons
import com.hellguy39.hellnotes.ui.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialog(
    showDialog: Boolean,
    onClose: () -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest =  onClose) {
            Surface(
                modifier = Modifier.fillMaxSize()
                    .padding(32.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Scaffold(
                    content = { innerPadding ->
                        content(innerPadding)
                    },
                    topBar = {
                        TopAppBar(
                            title = { /*Text( "Edit note", maxLines = 1, overflow = TextOverflow.Ellipsis)*/ },
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
                    }
                )
            }
        }
    }
}