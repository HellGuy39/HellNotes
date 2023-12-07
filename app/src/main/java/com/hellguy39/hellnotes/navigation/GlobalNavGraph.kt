package com.hellguy39.hellnotes.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.NavHost
import com.hellguy39.hellnotes.core.model.OnStartupArguments
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppScreen
import com.hellguy39.hellnotes.feature.backup.navigation.backupScreen
import com.hellguy39.hellnotes.feature.changelog.navigation.changelogScreen
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.label_edit.navigation.labelEditScreen
import com.hellguy39.hellnotes.feature.label_selection.navigation.labelSelectionScreen
import com.hellguy39.hellnotes.feature.language_selection.navigation.languageSelectionScreen
import com.hellguy39.hellnotes.feature.lock.navigation.lockScreen
import com.hellguy39.hellnotes.feature.lock_selection.navigation.lockSelectionScreen
import com.hellguy39.hellnotes.feature.lock_setup.navigation.lockSetupScreen
import com.hellguy39.hellnotes.feature.note_detail.navigations.noteDetailScreen
import com.hellguy39.hellnotes.feature.note_style_edit.navigation.noteStyleEditScreen
import com.hellguy39.hellnotes.feature.note_swipe_edit.navigation.noteSwipeEditScreen
import com.hellguy39.hellnotes.feature.on_boarding.navigation.onBoardingScreen
import com.hellguy39.hellnotes.feature.privacy_policy.navigation.privacyPolicyScreen
import com.hellguy39.hellnotes.feature.reminder_edit.navigations.reminderEditScreen
import com.hellguy39.hellnotes.feature.reset.navigation.resetScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.settingsScreen
import com.hellguy39.hellnotes.feature.startup.navigation.startupScreen
import com.hellguy39.hellnotes.feature.terms_and_conditions.navigation.termsAndConditionsScreen
import com.hellguy39.hellnotes.feature.update.navigation.updateScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlobalNavGraph(
    appState: HellNotesAppState,
    args: OnStartupArguments,
) {
    val navController = appState.navController

    NavHost(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true },
        navController = navController,
        startDestination = Screen.Startup.route
    ) {

        startupScreen(appState)

        homeScreen(navController, args)

        onBoardingScreen(appState)

        noteDetailScreen(appState)

        reminderEditScreen(appState)

        labelSelectionScreen(appState)

        searchScreen(appState)

        labelEditScreen(appState)

        settingsScreen(appState)

        lockScreen(appState)

        lockSelectionScreen(appState)

        lockSetupScreen(navController)

        languageSelectionScreen(appState)

        noteStyleEditScreen(appState)

        noteSwipeEditScreen(appState)

        aboutAppScreen(appState)

        changelogScreen(appState)

        termsAndConditionsScreen(appState)

        privacyPolicyScreen(appState)

        resetScreen(appState)

        updateScreen(appState)

        backupScreen(appState)

    }
}