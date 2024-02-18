package com.hellguy39.hellnotes.feature.changelog

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView

@Composable
fun ChangelogRoute(
    changelogViewModel: ChangelogViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    TrackScreenView(screenName = "ChangelogScreen")

    BackHandler { navigateBack() }

    val context = LocalContext.current

    val uiState by changelogViewModel.uiState.collectAsStateWithLifecycle()

    ChangelogScreen(
        onNavigationButtonClick = navigateBack,
        uiState = uiState,
        onTryAgain = {
            changelogViewModel.send(ChangelogUiEvent.TryAgain)
        },
        onOpenRelease = { release ->
            val uri = Uri.parse(release.htmlUrl.toString())
            val browserIntent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(browserIntent)
        },
    )
}
