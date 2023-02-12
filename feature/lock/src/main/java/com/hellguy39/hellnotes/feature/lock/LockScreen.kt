package com.hellguy39.hellnotes.feature.lock

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboard
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardSelection
import com.hellguy39.hellnotes.core.ui.components.PinDots
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LockScreen(
    uiState: LockUiState,
    numberKeyboardSelection: NumberKeyboardSelection,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.Bottom
                ),
            ) {

                val titleIcon = when (uiState.lockState) {
                    is LockState.Unlocked -> painterResource(id = HellNotesIcons.LockOpen)
                    else -> painterResource(id = HellNotesIcons.Lock)
                }

                val titleText = when (uiState.lockState) {
                    is LockState.Locked -> "Enter your PIN"
                    is LockState.Unlocked -> "Unlocked"
                    is LockState.WrongPin -> "Wrong PIN"
                }

                Icon(
                    painter = titleIcon,
                    contentDescription = null
                )

                AnimatedContent(
                    targetState = titleText,
                    transitionSpec = {
                        slideInVertically(animationSpec = tween(300)) +
                                fadeIn(animationSpec = tween(300)) with
                                slideOutVertically(animationSpec = tween(300)) +
                                fadeOut(animationSpec = tween(300))
                    }
                ) { targetTitle ->
                    Text(
                        text = targetTitle,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                PinDots(
                    pin = uiState.pin
                )

                Spacer(modifier = Modifier.weight(1f))

                NumberKeyboard(
                    selection = numberKeyboardSelection
                )
            }
        }
    )
}