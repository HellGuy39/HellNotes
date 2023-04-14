package com.hellguy39.hellnotes.feature.about_app

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.hellguy39.hellnotes.core.ui.navigations.*
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

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
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/hellguy39")
                )
                context.startActivity(browserIntent)
            },
            onPrivacyPolicy = navController::navigateToPrivacyPolicy,
            onProvideFeedback = { toast.show() },
            onRateOnPlayStore = { toast.show() },
            onCheckForUpdates = { toast.show() },
            onTermsAndConditions = navController::navigateToTermsAndConditions
        ),
    )
}