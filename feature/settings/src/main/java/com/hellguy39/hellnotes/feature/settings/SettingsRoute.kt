package com.hellguy39.hellnotes.feature.settings

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.system_features.LanguageHolder
import com.hellguy39.hellnotes.core.ui.components.rememberDialogState
import com.hellguy39.hellnotes.feature.settings.components.LanguageDialog
import com.hellguy39.hellnotes.feature.settings.components.PINDialog
import com.hellguy39.hellnotes.feature.settings.components.PinDialogSelection
import com.hellguy39.hellnotes.feature.settings.events.LanguageDialogEvents
import com.hellguy39.hellnotes.feature.settings.events.SettingsEvents

@Composable
fun SettingsRoute(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    languageHolder: LanguageHolder = settingsViewModel.languageHolder
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
            languageHolder.setLanguageCode(languageCode)
        }

        override fun getCurrentLanCode(): String {
            return languageHolder.getLanguageCode()
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

    LanguageDialog(
        isShowDialog = isShowLanguageDialog,
        events = languageDialogEvents,
    )

    SettingsScreen(
        onNavigationButtonClick = { navController.popBackStack() },
        languageDialogEvents = languageDialogEvents,
        appSettings = appSettings,
        settingsEvents = settingsEvents,
        isBioAuthAvailable = settingsViewModel.isBiometricAuthAvailable()
    )
}