package com.hellguy39.hellnotes.settings

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

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
//
//    Log.d("DEBUG", "LAN CODE: ${languageDialogEvents.getCurrentLanCode()}")

    SettingsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        //uiState = uiState,
        languageDialogEvents = languageDialogEvents,
        isShowLanguageDialog = isShowLanguageDialog,
        scrollBehavior = scrollBehavior
    )
}