package com.hellguy39.hellnotes.feature.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import com.hellguy39.hellnotes.core.ui.analytics.TrackScreenView
import com.hellguy39.hellnotes.feature.onboarding.util.OnBoardingPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingRoute(onFinish: () -> Unit = {}) {
    TrackScreenView(screenName = "OnBoardingScreen")

    val pages by remember {
        mutableStateOf(
            listOf(
                OnBoardingPage.First,
                OnBoardingPage.Second,
                OnBoardingPage.Third,
                OnBoardingPage.Fourth,
                OnBoardingPage.Fifth,
            ),
        )
    }

    val pagerState = rememberPagerState(pageCount = { pages.size })

    OnBoardingScreen(
        pages = pages,
        pagerState = pagerState,
        onFinish = onFinish,
        onSkip = onFinish,
    )
}
