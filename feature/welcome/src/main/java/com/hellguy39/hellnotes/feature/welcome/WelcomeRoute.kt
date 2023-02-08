package com.hellguy39.hellnotes.feature.welcome

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.feature.welcome.util.OnBoardingPage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeRoute(
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    onFinish: () -> Unit = {}
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState()

    WelcomeScreen(
        pages = pages,
        pagerState = pagerState,
        onFinish = {
            welcomeViewModel.saveOnBoardingState(completed = true)
            onFinish()
        }
    )
}