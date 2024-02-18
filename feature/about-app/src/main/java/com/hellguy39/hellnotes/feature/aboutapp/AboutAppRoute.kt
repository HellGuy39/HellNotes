package com.hellguy39.hellnotes.feature.aboutapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.feature.aboutapp.util.openGithub
import com.hellguy39.hellnotes.feature.aboutapp.util.provideFeedback

@Composable
fun AboutAppRoute(
    navigateBack: () -> Unit,
    navigateToReset: () -> Unit,
    navigateToChangelog: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToTermsAndConditions: () -> Unit,
) {
    TrackScreenView(screenName = "AboutScreen")

    val context = LocalContext.current

    AboutAppScreen(
        onNavigationButtonClick = { navigateBack() },
        selection =
            AboutAppScreenSelection(
                onReset = navigateToReset,
                onChangelog = navigateToChangelog,
                onGithub = { context.openGithub() },
                onPrivacyPolicy = navigateToPrivacyPolicy,
                onProvideFeedback = { context.provideFeedback() },
                onTermsAndConditions = { navigateToTermsAndConditions() },
            ),
    )
}
