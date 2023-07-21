package com.hellguy39.hellnotes.feature.home.list.labels.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = HellNotesStrings.Title.Labels),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
        },
//        navigationIcon = {
//            IconButton(onClick = onNavigation) {
//                Icon(
//                    painter = painterResource(id = HellNotesIcons.Menu),
//                    contentDescription = null
//                )
//            }
//        },
    )
}