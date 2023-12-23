package com.hellguy39.hellnotes.feature.aboutapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.feature.aboutapp.components.AboutAppScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    onNavigationButtonClick: () -> Unit = {},
    selection: AboutAppScreenSelection = AboutAppScreenSelection(),
) {
    BackHandler { onNavigationButtonClick() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier =
            Modifier.fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { innerPadding ->
            AboutAppScreenContent(
                innerPadding = innerPadding,
                selection = selection,
            )
        },
        topBar = {
            HNLargeTopAppBar(
                onNavigationButtonClick = onNavigationButtonClick,
                title = stringResource(id = HellNotesStrings.AppName),
                scrollBehavior = scrollBehavior,
            )
        },
    )
}

data class AboutAppScreenSelection(
    val onGithub: () -> Unit = {},
    val onChangelog: () -> Unit = {},
    val onPrivacyPolicy: () -> Unit = {},
    val onTermsAndConditions: () -> Unit = {},
    val onProvideFeedback: () -> Unit = {},
    val onReset: () -> Unit = {},
)

@Preview(showBackground = true)
@Composable
fun AboutAppScreenPreview() {
    HellNotesTheme {
        AboutAppScreen()
    }
}
