package com.hellguy39.hellnotes.feature.home.note_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.home.util.DrawerItem

@Composable
fun DrawerSheetContent(
    selectedItem: DrawerItem?,
    mainItems: List<DrawerItem>,
    labelItems: List<DrawerItem>,
    staticItems: List<DrawerItem>
) {
    ModalDrawerSheet {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Hell")
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        append("Notes")
                    }
                },
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(12.dp)
            )
        }

        mainItems.forEach { item ->
            CustomNavDrawerItem(
                drawerItem = item,
                selectedItem = selectedItem
            )
        }

        if (labelItems.isNotEmpty()) {

            Divider(
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceTint
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Labels",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {  },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Edit),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            labelItems.forEach { item ->
                CustomNavDrawerItem(
                    drawerItem = item,
                    selectedItem = selectedItem
                )
            }

            Divider(
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.surfaceTint
            )

        }

        staticItems.forEach { item ->
            CustomNavDrawerItem(
                drawerItem = item,
                selectedItem = selectedItem
            )
        }

    }
}

@Composable
fun CustomNavDrawerItem(
    drawerItem: DrawerItem,
    selectedItem: DrawerItem?
) {
    NavigationDrawerItem(
        icon = { drawerItem.icon?.let { Icon(it, contentDescription = null) } },
        label = { Text(text = drawerItem.title, style = MaterialTheme.typography.labelLarge) },
        selected = selectedItem == drawerItem,
        onClick = { drawerItem.onClick(drawerItem) },
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}