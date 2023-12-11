package com.hellguy39.hellnotes.feature.aboutapp

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.aboutapp.util.openGithub
import com.hellguy39.hellnotes.feature.aboutapp.util.provideFeedback

@Composable
fun AboutAppRoute(
    navigateBack: () -> Unit,
    navigateToReset: () -> Unit,
    navigateToChangelog: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToTermsAndConditions: () -> Unit,
    navigateToUpdate: () -> Unit,
) {
    val context = LocalContext.current
    val toast = Toast.makeText(context, context.getString(HellNotesStrings.Toast.ComingSoon), Toast.LENGTH_SHORT)

    AboutAppScreen(
        onNavigationButtonClick = { navigateBack() },
        selection =
            AboutAppScreenSelection(
                onReset = navigateToReset,
                onChangelog = navigateToChangelog,
                onGithub = { context.openGithub() },
                onPrivacyPolicy = navigateToPrivacyPolicy,
                onProvideFeedback = { context.provideFeedback() },
                onRateOnPlayStore = { toast.show() },
                onCheckForUpdates = {
//                    if (ProjectInfoProvider.appConfig.isDebug) {
//                        navigateToUpdate()
//                    } else {
                    toast.show()
                    // }
                },
                onTermsAndConditions = { navigateToTermsAndConditions() },
            ),
    )
}
