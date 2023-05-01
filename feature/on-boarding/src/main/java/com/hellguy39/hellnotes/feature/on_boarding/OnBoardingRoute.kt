package com.hellguy39.hellnotes.feature.on_boarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.hellguy39.hellnotes.core.ui.system.BackHandler
import com.hellguy39.hellnotes.feature.on_boarding.util.OnBoardingPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingRoute(
    onFinish: () -> Unit = {}
) {
    BackHandler(
        onBack = onFinish
    )

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third,
        OnBoardingPage.Fourth,
        OnBoardingPage.Fifth
    )
    val pagerState = rememberPagerState()

    WelcomeScreen(
        pages = pages,
        pagerState = pagerState,
        onFinish = onFinish,
        onSkip = onFinish
    )
}