package com.hellguy39.hellnotes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hellguy39.hellnotes.core.model.util.OnStartupArguments
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.about_app.navigation.aboutAppScreen
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

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SetupNavGraph(
    args: OnStartupArguments,
) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            },
        navController = navController,
        startDestination = Screen.Startup.route
    ) {

        startupScreen(navController)

        homeScreen(navController, args)

        onBoardingScreen(navController)

        noteDetailScreen(navController)

        reminderEditScreen(navController)

        labelSelectionScreen(navController)

        searchScreen(navController)

        labelEditScreen(navController)

        settingsScreen(navController)

        lockScreen(navController)

        lockSelectionScreen(navController)

        lockSetupScreen(navController)

        languageSelectionScreen(navController)

        noteStyleEditScreen(navController)

        noteSwipeEditScreen(navController)

        aboutAppScreen(navController)

        changelogScreen(navController)

        termsAndConditionsScreen(navController)

        privacyPolicyScreen(navController)

        resetScreen(navController)

    }
}