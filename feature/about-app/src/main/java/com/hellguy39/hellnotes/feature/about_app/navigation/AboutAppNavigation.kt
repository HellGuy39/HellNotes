package com.hellguy39.hellnotes.feature.about_app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.HellNotesAppState
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToChangelog
import com.hellguy39.hellnotes.core.ui.navigations.navigateToPrivacyPolicy
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReset
import com.hellguy39.hellnotes.core.ui.navigations.navigateToTermsAndConditions
import com.hellguy39.hellnotes.core.ui.navigations.navigateToUpdate
import com.hellguy39.hellnotes.feature.about_app.AboutAppRoute

fun NavGraphBuilder.aboutAppScreen(
    appState: HellNotesAppState,
) {
    composable(
        route = Screen.AboutApp.route,
        arguments = listOf(),
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) { from ->
        AboutAppRoute(
            navigateBack = { appState.navigateUp() },
            navigateToReset = { appState.navigateToReset(from) },
            navigateToChangelog = { appState.navigateToChangelog(from) },
            navigateToPrivacyPolicy = { appState.navigateToPrivacyPolicy(from) },
            navigateToTermsAndConditions = { appState.navigateToTermsAndConditions(from) },
            navigateToUpdate = { appState.navigateToUpdate(from) },
        )
    }
}
