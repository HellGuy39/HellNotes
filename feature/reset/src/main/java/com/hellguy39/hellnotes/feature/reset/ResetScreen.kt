package com.hellguy39.hellnotes.feature.reset

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.components.CustomCheckbox
import com.hellguy39.hellnotes.core.ui.components.items.SelectionItemDefault
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetScreen(
    onNavigationButtonClick: () -> Unit,
    onReset: (resetDatabase: Boolean, resetSettings: Boolean) -> Unit
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    var isResetDatabase by rememberSaveable { mutableStateOf(false) }
    var isResetSettings by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.Title.Reset)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(256.dp),
                    painter = painterResource(id = HellNotesIcons.RestartAlt),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )

                Spacer(modifier = Modifier.height(0.dp))

                Text(
                    text = stringResource(id = HellNotesStrings.Helper.SelectActions),
                    style = MaterialTheme.typography.titleLarge,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    CustomCheckboxItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isResetDatabase,
                                onClick = { isResetDatabase = !isResetDatabase },
                                role = Role.RadioButton
                            ),
                        title = stringResource(id = HellNotesStrings.Checkbox.ClearDatabase),
                        checked = isResetDatabase,
                    )
                    CustomCheckboxItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isResetSettings,
                                onClick = { isResetSettings = !isResetSettings },
                                role = Role.RadioButton
                            ),
                        title = stringResource(id = HellNotesStrings.Checkbox.ResetSettings),
                        checked = isResetSettings,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    onClick = { onReset(isResetDatabase, isResetSettings) },
                    enabled = isResetSettings || isResetDatabase,
                    //contentPadding = ButtonDefaults.ButtonWithIconContentPadding
                ) {
//                    Icon(
//                        painter = painterResource(id = HellNotesIcons.RestartAlt),
//                        contentDescription = null,
//                        modifier = Modifier.size(ButtonDefaults.IconSize)
//                    )
//                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Reset),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    )
}

@Composable
fun CustomCheckboxItem(
    modifier: Modifier = Modifier,
    //heroIcon: Painter ,
    title: String = "",
    checked: Boolean,
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            Box(modifier = Modifier) {
                Checkbox(
                    modifier = Modifier
                        .padding(0.dp),
                    checked = checked,
                    onCheckedChange = null
                )
            }
        }
    }
}