package com.hellguy39.hellnotes.feature.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces
import com.hellguy39.hellnotes.feature.onboarding.util.OnBoardingPage
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun WelcomeScreen(
    pagerState: PagerState,
    pages: List<OnBoardingPage>,
    onFinish: () -> Unit,
    onSkip: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    val isFinished = pagerState.currentPage == pages.size - 1

    val animatedProgress by animateFloatAsState(
        targetValue = (1.0f / (pages.size - 1)) * (pagerState.currentPage),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = "animatedProgress",
    )

    val animatedSkipButton by animateFloatAsState(
        targetValue = if (pagerState.currentPage == pages.size - 1) 0f else 1f,
        animationSpec = tween(300),
        label = "animatedSkipButton",
    )

    Scaffold(
        modifier =
            Modifier
                .semantics { testTagsAsResourceId = true },
        topBar = {
            TopAppBar(
                title = {
                    LinearProgressIndicator(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Spaces.extraLarge * 2)
                                .clip(MaterialTheme.shapes.medium),
                        progress = animatedProgress,
                    )
                },
            )
        },
        bottomBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Spaces.medium)
                        .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    modifier =
                        Modifier
                            .testTag("button_skip")
                            .alpha(animatedSkipButton),
                    onClick = onSkip,
                    enabled = animatedSkipButton == 1f,
                ) {
                    Text(text = stringResource(id = HellNotesStrings.Button.Skip))
                }

                Button(
                    modifier =
                        Modifier
                            .testTag("button_next"),
                    onClick = {
                        if (isFinished) {
                            onFinish()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    },
                ) {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.finish(isFinished)),
                    )
                }
            }
        },
    ) { paddingValues ->
        HorizontalPager(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            contentDescription = null,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth(),
            text = stringResource(id = onBoardingPage.title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .padding(top = 24.dp),
            text = stringResource(id = onBoardingPage.description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}
