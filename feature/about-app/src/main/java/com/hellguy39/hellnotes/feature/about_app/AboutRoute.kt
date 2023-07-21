package com.hellguy39.hellnotes.feature.about_app

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.domain.ProjectInfoProvider
import com.hellguy39.hellnotes.core.ui.resource.HellNotesStrings
import com.hellguy39.hellnotes.feature.about_app.util.provideFeedback

@Composable
fun AboutRoute(
    aboutViewModel: AboutViewModel
) {
    val context = LocalContext.current
    val toast = Toast.makeText(context, context.getString(HellNotesStrings.Toast.ComingSoon), Toast.LENGTH_SHORT)

    AboutAppScreen(
        onNavigationButtonClick = {},
        selection = AboutAppScreenSelection(
            onReset = {  },
            onChangelog = {},
            onGithub = {},
            onPrivacyPolicy = {},
            onProvideFeedback = context::provideFeedback,
            onRateOnPlayStore = { toast.show() },
            onCheckForUpdates = {
                if (ProjectInfoProvider.appConfig.isDebug) {
                    //navController.navigateToUpdate()
                } else {
                    toast.show()
                }
            },
            onTermsAndConditions = {}
        ),
    )
}