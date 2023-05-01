package com.hellguy39.hellnotes.feature.startup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.hellguy39.hellnotes.core.model.LockScreenType
import com.hellguy39.hellnotes.core.ui.navigations.Screen
import com.hellguy39.hellnotes.core.ui.navigations.navigateToHome
import com.hellguy39.hellnotes.core.ui.navigations.navigateToLock
import com.hellguy39.hellnotes.core.ui.navigations.navigateToOnBoarding

@Composable
fun StartupRoute(
    navController: NavController,
    startupViewModel: StartupViewModel = hiltViewModel()
) {
    val startupState by startupViewModel.startupState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = startupState) {
        startupState.let { state ->
            when (state) {
                is StartupState.Success -> {

                    if (!state.onBoardingState) {
                        navController.navigateToOnBoarding()
                    } else if (state.securityState.lockType != LockScreenType.None) {
                        navController.navigateToLock(
                            navOptions {
                                popUpTo(Screen.Startup.route) {
                                    inclusive = true
                                }
                            }
                        )
                    } else {
                        navController.navigateToHome(
                            navOptions {
                                popUpTo(Screen.Startup.route) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                }
                else -> Unit
            }
        }
    }
}