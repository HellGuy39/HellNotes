package com.hellguy39.hellnotes.feature.lock_setup

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.components.CustomDialogState
import com.hellguy39.hellnotes.core.ui.components.top_bars.CustomLargeTopAppBar

@Composable
fun LockSetupScreen(
    uiState: LockSetupUiState,
    onNavigationBack: () -> Unit,
    onCodeReceived: (String) -> Unit
) {
    when(uiState.newLockScreenType) {
        LockScreenType.None -> {

        }
        LockScreenType.Pin -> {
            LockSetupPin(
                onNavigationBack = onNavigationBack,
                onPinEntered = onCodeReceived
            )
        }
        else -> {

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LockSetupPin(
    onNavigationBack: () -> Unit,
    onPinEntered: (String) -> Unit
) {
    var value by rememberSaveable { mutableStateOf("") }
    var pin by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable(saver = SetupPinState.Saver()) { mutableStateOf(SetupPinState.SetPin) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    fun onValueChange(newText: String) {

        value = newText
        isError = false

        if(value.length >= 4) {
            when(state) {
                SetupPinState.SetPin -> {
                    pin = value
                    state = SetupPinState.ConfirmPin
                }
                SetupPinState.ConfirmPin -> {
                    if (pin == value) {
                        onPinEntered(pin)
                    } else {
                        isError = true
                        errorMessage = "Wrong pin"
                    }
                }
            }
            value = ""
        }
    }

    val title = when (state) {
        SetupPinState.SetPin -> "Set a PIN"
        SetupPinState.ConfirmPin -> "Re-enter your pin"
    }

    val subtitle = when (state) {
        SetupPinState.SetPin -> "For added security, set a PIN to unlock all your notes"
        SetupPinState.ConfirmPin -> ""
    }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    LaunchedEffect(key1 = state) {
        if (state == SetupPinState.SetPin || state == SetupPinState.ConfirmPin) {
            focusRequester.requestFocus()
        }
    }

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
        }
    ) { state ->
        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 32.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = value,
                        onValueChange = { newText -> onValueChange(newText) },
                        singleLine = true,
                        isError = isError,
                        supportingText = {
                            if (!isError) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Pin must be only 4 digits",
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = errorMessage,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.error
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        )
                    )
                }
            },
            topBar = {
                CustomLargeTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onNavigationButtonClick = onNavigationBack,
                    title = title
                )
            }
        )
    }
}

sealed class SetupPinState {
    object SetPin: SetupPinState()
    object ConfirmPin: SetupPinState()

    fun parse(): String {
        return when(this) {
            SetPin -> SET_PIN
            ConfirmPin -> CONFIRM_PIN
        }
    }

    companion object {

        private const val SET_PIN = "set_pin"
        private const val CONFIRM_PIN = "confirm_pin"

        fun from(s: String?): SetupPinState {
            return when(s) {
                SET_PIN -> SetPin
                CONFIRM_PIN -> ConfirmPin
                else -> SetPin
            }
        }

        fun Saver(): Saver<MutableState<SetupPinState>, *> = Saver(
            save = { state ->
                state.value.parse()
            },
            restore = { state ->
                mutableStateOf(from(state))
            }
        )

    }

}