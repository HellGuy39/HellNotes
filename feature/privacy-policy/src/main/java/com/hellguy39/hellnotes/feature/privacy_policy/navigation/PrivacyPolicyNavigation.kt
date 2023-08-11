package com.hellguy39.hellnotes.feature.privacy_policy.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.ui.model.Screen
import com.hellguy39.hellnotes.feature.privacy_policy.PrivacyPolicyRoute

fun NavGraphBuilder.privacyPolicyScreen(
    navController: NavController
) {
    composable(
        route = Screen.PrivacyPolicy.route,
    ) {
        PrivacyPolicyRoute(navController = navController)
    }
}
