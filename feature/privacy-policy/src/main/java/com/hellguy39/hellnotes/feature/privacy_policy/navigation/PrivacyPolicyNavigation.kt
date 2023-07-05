package com.hellguy39.hellnotes.feature.privacy_policy.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.feature.privacy_policy.PrivacyPolicyRoute

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.privacyPolicyScreen(
    navController: NavController
) {
    composable(
        route = Screen.PrivacyPolicy.route,
    ) {
        PrivacyPolicyRoute(navController = navController)
    }
}
