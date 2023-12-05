package com.hellguy39.hellnotes.feature.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.core.model.LockScreenType

@Composable
fun StartupRoute(
    navigateToOnBoarding: () -> Unit,
    navigateToLock: () -> Unit,
    navigateToHome: () -> Unit,
    startupViewModel: StartupViewModel = hiltViewModel()
) {
    val startupState by startupViewModel.startupState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = startupState) {
        startupState.let { state ->
            when (state) {
                is StartupState.Success -> {
                    if (!state.onBoardingState) {
                        navigateToOnBoarding()
                    } else if (state.securityState.lockType != LockScreenType.None) {
                        navigateToLock()
                    } else {
                        navigateToHome()
                    }
                }
                else -> Unit
            }
        }
    }
}