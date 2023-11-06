package com.hellguy39.hellnotes.feature.on_boarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.feature.on_boarding.util.OnBoardingPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    pagerState: PagerState,
    pages: List<OnBoardingPage>,
    onFinish: () -> Unit,
    onSkip: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        content = { paddingValues ->
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                state = pagerState,
                verticalAlignment = Alignment.Top,
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }
        },
        bottomBar = {

            val animatedProgress by animateFloatAsState(
                targetValue = (1.0f / (pages.size - 1)) * (pagerState.currentPage),
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                label = "animatedProgress"
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val animatedSkipButton by animateFloatAsState(
                    targetValue = if (pagerState.currentPage == pages.size - 1) 0f else 1f,
                    animationSpec = tween(300),
                    label = "animatedSkipButton"
                )

                TextButton(
                    modifier = Modifier
                        .width(96.dp)
                        .alpha(animatedSkipButton),
                    onClick = onSkip
                ) {
                    Text(text = stringResource(id = HellNotesStrings.Button.Skip))
                }

                LinearProgressIndicator(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    progress = animatedProgress
                )

                Button(
                    modifier = Modifier.width(96.dp),
                    onClick = {
                        if (pagerState.currentPage == pages.size - 1) {
                            onFinish()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                            }
                        }
                    }
                ) {
                    Text(
                        text = if (pagerState.currentPage == pages.size - 1)
                            stringResource(id = HellNotesStrings.Button.Finish)
                        else
                            stringResource(id = HellNotesStrings.Button.Next)
                    )
                }
            }
        }
    )
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.7f),
            painter = painterResource(id = onBoardingPage.image),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = onBoardingPage.title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .padding(top = 24.dp),
            text = stringResource(id = onBoardingPage.description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}