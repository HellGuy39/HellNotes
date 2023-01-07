package com.hellguy39.hellnotes.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.settings.components.LanguageDialog
import com.hellguy39.hellnotes.settings.components.SettingsScreenContent
import com.hellguy39.hellnotes.settings.components.SettingsTopAppBar
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigationButtonClick: () -> Unit,
    languageDialogEvents: LanguageDialogEvents,
    isShowLanguageDialog: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    BackHandler(onBack = onNavigationButtonClick)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->

            LanguageDialog(
                isShowDialog = isShowLanguageDialog,
                events = languageDialogEvents,
            )

            SettingsScreenContent(
                innerPadding = innerPadding,
                languageDialogEvents = languageDialogEvents
            )
        },
        topBar = {
            SettingsTopAppBar(
                scrollBehavior = scrollBehavior,
                onNavigationButtonClick = onNavigationButtonClick
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var isShowLanguageDialog by remember { mutableStateOf(false) }
    val languageDialogEvents = object : LanguageDialogEvents {
        override fun show() { isShowLanguageDialog = true }
        override fun dismiss() { isShowLanguageDialog = false }
        override fun onLanguageSelected(languageCode: String) = Unit
        override fun getCurrentLanCode(): String = ""
    }

    SettingsScreen(
        onNavigationButtonClick = {  },
        languageDialogEvents = languageDialogEvents,
        isShowLanguageDialog = isShowLanguageDialog,
        scrollBehavior = scrollBehavior
    )
}