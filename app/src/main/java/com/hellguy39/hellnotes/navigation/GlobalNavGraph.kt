package com.hellguy39.hellnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.state.AppState
import com.hellguy39.hellnotes.feature.aboutapp.navigation.aboutAppScreen
import com.hellguy39.hellnotes.feature.backup.navigation.backupScreen
import com.hellguy39.hellnotes.feature.changelog.navigation.changelogScreen
import com.hellguy39.hellnotes.feature.home.navigation.homeScreen
import com.hellguy39.hellnotes.feature.labeledit.navigation.labelEditScreen
import com.hellguy39.hellnotes.feature.labelselection.navigation.labelSelectionScreen
import com.hellguy39.hellnotes.feature.languageselection.navigation.languageSelectionScreen
import com.hellguy39.hellnotes.feature.lock.LockFullScreenDialog
import com.hellguy39.hellnotes.feature.lock.LockViewModel
import com.hellguy39.hellnotes.feature.lock.navigation.lockScreen
import com.hellguy39.hellnotes.feature.lockselection.navigation.lockSelectionScreen
import com.hellguy39.hellnotes.feature.locksetup.navigation.lockSetupScreen
import com.hellguy39.hellnotes.feature.notedetail.navigations.noteDetailScreen
import com.hellguy39.hellnotes.feature.notestyleedit.navigation.noteStyleEditScreen
import com.hellguy39.hellnotes.feature.noteswipeedit.navigation.noteSwipeEditScreen
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingFullScreenDialog
import com.hellguy39.hellnotes.feature.onboarding.OnBoardingViewModel
import com.hellguy39.hellnotes.feature.onboarding.navigation.onBoardingScreen
import com.hellguy39.hellnotes.feature.privacypolicy.navigation.privacyPolicyScreen
import com.hellguy39.hellnotes.feature.reminderedit.navigations.reminderEditScreen
import com.hellguy39.hellnotes.feature.reset.navigation.resetScreen
import com.hellguy39.hellnotes.feature.search.navigation.searchScreen
import com.hellguy39.hellnotes.feature.settings.navigation.settingsScreen
import com.hellguy39.hellnotes.feature.termsandconditions.navigation.termsAndConditionsScreen
import com.hellguy39.hellnotes.feature.update.navigation.updateScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GlobalNavGraph(
    appState: AppState,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    lockViewModel: LockViewModel = hiltViewModel(),
) {
    val navController = appState.navController

    val onBoardingState by onBoardingViewModel.onBoardingState.collectAsStateWithLifecycle()

    val isLocked by lockViewModel.isLocked.collectAsStateWithLifecycle()

    OnBoardingFullScreenDialog(
        isShowDialog = onBoardingState.isVisible,
        onFinish = { onBoardingViewModel.finishOnBoarding() },
    )

    LockFullScreenDialog(
        isShowDialog = isLocked,
    )

    NavHost(
        modifier =
            Modifier
                .semantics { testTagsAsResourceId = true },
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        homeScreen(appState)

        onBoardingScreen(appState)

        noteDetailScreen(appState)

        reminderEditScreen(appState)

        labelSelectionScreen(appState)

        searchScreen(appState)

        labelEditScreen(appState)

        settingsScreen(appState)

        lockScreen(appState)

        lockSelectionScreen(appState)

        lockSetupScreen(appState)

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
