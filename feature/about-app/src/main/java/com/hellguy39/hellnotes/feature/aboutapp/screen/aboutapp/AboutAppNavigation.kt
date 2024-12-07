package com.hellguy39.hellnotes.feature.aboutapp.screen.aboutapp

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hellguy39.hellnotes.core.common.navigation.Screen
import com.hellguy39.hellnotes.core.ui.animations.fadeEnterTransition
import com.hellguy39.hellnotes.core.ui.animations.fadeExitTransition
import com.hellguy39.hellnotes.core.ui.state.GraphState
import com.hellguy39.hellnotes.feature.aboutapp.screen.changelog.navigateToChangelog
import com.hellguy39.hellnotes.feature.aboutapp.screen.privacypolicy.navigateToPrivacyPolicy
import com.hellguy39.hellnotes.feature.aboutapp.screen.reset.navigateToReset
import com.hellguy39.hellnotes.feature.aboutapp.screen.termsandconditions.navigateToTermsAndConditions

internal object AboutAppScreen : Screen {
    override val endpoint: String = "about_app"
}

internal fun NavGraphBuilder.aboutAppScreen(
    exitGraph: () -> Unit,
    aboutAppState: GraphState
) {
    composable(
        route = AboutAppScreen.endpoint,
        arguments = listOf(),
        enterTransition = { fadeEnterTransition() },
        exitTransition = { fadeExitTransition() },
        popEnterTransition = { fadeEnterTransition() },
        popExitTransition = { fadeExitTransition() },
    ) { from ->
        AboutAppRoute(
            navigateBack = exitGraph,
            navigateToReset = { aboutAppState.navigateToReset(from) },
            navigateToChangelog = { aboutAppState.navigateToChangelog(from) },
            navigateToPrivacyPolicy = { aboutAppState.navigateToPrivacyPolicy(from) },
            navigateToTermsAndConditions = { aboutAppState.navigateToTermsAndConditions(from) },
        )
    }
}
