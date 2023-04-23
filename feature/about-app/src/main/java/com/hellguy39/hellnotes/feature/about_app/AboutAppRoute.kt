package com.hellguy39.hellnotes.feature.about_app

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.ui.navigations.*
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.about_app.util.openGithub
import com.hellguy39.hellnotes.feature.about_app.util.provideFeedback

@Composable
fun AboutAppRoute(
    navController: NavController
) {
    val context = LocalContext.current
    val toast = Toast.makeText(context, context.getString(HellNotesStrings.Helper.ComingSoon), Toast.LENGTH_SHORT)

    AboutAppScreen(
        onNavigationButtonClick = navController::popBackStack,
        selection = AboutAppScreenSelection(
            onReset = navController::navigateToReset,
            onChangelog = navController::navigateToChangelog,
            onGithub = {
                context.openGithub()
            },
            onPrivacyPolicy = navController::navigateToPrivacyPolicy,
            onProvideFeedback = {
                context.provideFeedback()
            },
            onRateOnPlayStore = { toast.show() },
            onCheckForUpdates = {
                if (ProjectInfoProvider.appConfig.isDebug) {
                    navController.navigateToUpdate()
                } else {
                    toast.show()
                }
            },
            onTermsAndConditions = navController::navigateToTermsAndConditions
        ),
    )
}