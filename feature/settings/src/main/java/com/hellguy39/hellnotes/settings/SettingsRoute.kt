package com.hellguy39.hellnotes.settings

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents

@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    //val uiState by settingsViewModel.uiState.collectAsState()
    var isShowLanguageDialog by remember { mutableStateOf(false) }

    val languageDialogEvents = object : LanguageDialogEvents {
        override fun show() { isShowLanguageDialog = true }
        override fun dismiss() { isShowLanguageDialog = false }
        override fun onLanguageSelected(languageCode: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.getSystemService(LocaleManager::class.java).applicationLocales =
                    LocaleList.forLanguageTags(languageCode)
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
            }
        }

        override fun getCurrentLanCode(): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val appLocales = context.getSystemService(LocaleManager::class.java).applicationLocales
                appLocales.toLanguageTags()
            } else {
                val appLocales = AppCompatDelegate.getApplicationLocales()
                appLocales.toLanguageTags()
            }
        }
    }

    SettingsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        //uiState = uiState,
        languageDialogEvents = languageDialogEvents,
        isShowLanguageDialog = isShowLanguageDialog
    )
}