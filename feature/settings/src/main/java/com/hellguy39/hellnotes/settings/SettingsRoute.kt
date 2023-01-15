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
import com.hellguy39.hellnotes.components.rememberDialogState
import com.hellguy39.hellnotes.settings.components.PINDialog
import com.hellguy39.hellnotes.settings.components.PinDialogSelection
import com.hellguy39.hellnotes.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.settings.events.SettingsEvents

@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val appSettings by settingsViewModel.appSettings.collectAsState()

    val context = LocalContext.current
    var isShowLanguageDialog by remember { mutableStateOf(false) }

    val pinDialogState = rememberDialogState()

    val settingsEvents = object : SettingsEvents {
        override fun setupPIN() { pinDialogState.show() }
        override fun updatePIN() { pinDialogState.show() }
        override fun deletePIN() { settingsViewModel.deletePin() }
        override fun setUseBiometric(isUseBio: Boolean) {
            settingsViewModel.setIsUseBiometric(isUseBio)
        }
    }

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

    PINDialog(
        state = pinDialogState,
        selection = PinDialogSelection(
            existingPin = appSettings.appPin,
            onPinEntered = { newPin ->
                settingsViewModel.setPin(newPin)
            }
        )
    )

    SettingsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        languageDialogEvents = languageDialogEvents,
        isShowLanguageDialog = isShowLanguageDialog,
        appSettings = appSettings,
        settingsEvents = settingsEvents,
        isBioAuthAvailable = settingsViewModel.isBiometricAuthAvailable()
    )
}