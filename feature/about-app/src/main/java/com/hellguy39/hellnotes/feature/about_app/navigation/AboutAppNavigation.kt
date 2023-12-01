package com.hellguy39.hellnotes.feature.about_app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.UiDefaults
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToChangelog
import com.hellguy39.hellnotes.core.ui.navigations.navigateToPrivacyPolicy
import com.hellguy39.hellnotes.core.ui.navigations.navigateToReset
import com.hellguy39.hellnotes.core.ui.navigations.navigateToTermsAndConditions
import com.hellguy39.hellnotes.core.ui.navigations.navigateToUpdate
import com.hellguy39.hellnotes.feature.about_app.AboutAppRoute

fun NavGraphBuilder.aboutAppScreen(
    navController: NavController
) {
    composable(
        route = Screen.AboutApp.route,
        arguments = listOf(),
        enterTransition = { null },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = { null },
    ) {
        AboutAppRoute(
            navigateBack = { navController.popBackStack() },
            navigateToReset = { navController.navigateToReset() },
            navigateToChangelog = { navController.navigateToChangelog() },
            navigateToPrivacyPolicy = { navController.navigateToPrivacyPolicy() },
            navigateToTermsAndConditions = { navController.navigateToTermsAndConditions() },
            navigateToUpdate = { navController.navigateToUpdate() },
        )
    }
}
