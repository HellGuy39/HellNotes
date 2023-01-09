package com.hellguy39.hellnotes.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.BackHandler
import com.hellguy39.hellnotes.model.AppSettings
import com.hellguy39.hellnotes.settings.components.*
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.settings.events.PINDialogEvents
import com.hellguy39.hellnotes.settings.events.SettingsEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigationButtonClick: () -> Unit,
    languageDialogEvents: LanguageDialogEvents,
    isShowLanguageDialog: Boolean,
    appSettings: AppSettings,
    settingsEvents: SettingsEvents
) {
    BackHandler(onBack = onNavigationButtonClick)

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

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
                languageDialogEvents = languageDialogEvents,
                appSettings = appSettings,
                settingsEvents = settingsEvents,
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

//@Preview(showBackground = true)
//@Composable
//fun SettingsScreenPreview() {
//
//    var isShowLanguageDialog by remember { mutableStateOf(false) }
//    var isShowPinDialog by remember { mutableStateOf(false) }
//
//    val pinDialogEvents = object : PINDialogEvents {
//        override fun show() { isShowPinDialog = true }
//        override fun dismiss() { isShowPinDialog = false }
//        override fun onNewPin(pin: String) {}
//    }
//
//    val languageDialogEvents = object : LanguageDialogEvents {
//        override fun show() { isShowLanguageDialog = true }
//        override fun dismiss() { isShowLanguageDialog = false }
//        override fun onLanguageSelected(languageCode: String) = Unit
//        override fun getCurrentLanCode(): String = ""
//    }
//
//    val settingsEvents = object : SettingsEvents {
//        override fun setupPIN() {}
//        override fun updatePIN() {}
//        override fun deletePIN() {}
//        override fun setUseBiometric(isUseBio: Boolean) {}
//    }
//
//    SettingsScreen(
//        onNavigationButtonClick = {  },
//        languageDialogEvents = languageDialogEvents,
//        isShowLanguageDialog = isShowLanguageDialog,
//        appSettings = AppSettings(),
//        settingsEvents = settingsEvents,
//        isShowPinDialog = false,
//        pinDialogEvents = pinDialogEvents
//    )
//}