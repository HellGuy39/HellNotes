/*
 * Copyright 2024 Aleksey Gadzhiev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hellguy39.hellnotes.feature.settings.screen.locksetup

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.topappbars.HNLargeTopAppBar
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import com.hellguy39.hellnotes.core.ui.values.Spaces

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
internal fun LockSetupPinScreen(
    onNavigationBack: () -> Unit,
    onPinEntered: (String) -> Unit,
) {
    val context = LocalContext.current

    var value by rememberSaveable { mutableStateOf("") }
    var pin by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable(saver = SetupPinState.Saver()) { mutableStateOf(SetupPinState.SetPin) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    fun onValueChange(newText: String) {
        value = newText
        isError = false
    }

    fun onConfirm() {
        if (value.length >= 4) {
            when (state) {
                SetupPinState.SetPin -> {
                    pin = value
                    state = SetupPinState.ConfirmPin
                }
                SetupPinState.ConfirmPin -> {
                    if (pin == value) {
                        onPinEntered(pin)
                    } else {
                        isError = true
                        errorMessage = context.getString(AppStrings.Supporting.WrongPin)
                    }
                }
            }
            value = ""
        }
    }

    fun onClear() {
        value = ""
    }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    LaunchedEffect(key1 = state) {
        focusRequester.requestFocus()
    }

    val duration = 300

    AnimatedContent(
        targetState = state,
        transitionSpec = {
            slideInHorizontally(animationSpec = tween(duration)) { fullWidth -> fullWidth } + fadeIn(animationSpec = tween(duration)) with
                slideOutHorizontally(animationSpec = tween(duration)) { fullWidth -> -fullWidth } + fadeOut(animationSpec = tween(duration))
        },
    ) { state ->
        Scaffold(
            content = { paddingValues ->
                Column(
                    modifier =
                        Modifier
                            .padding(horizontal = Spaces.medium, vertical = Spaces.small)
                            .padding(top = paddingValues.calculateTopPadding())
                            .imePadding()
                            .navigationBarsPadding(),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            space = 32.dp,
                            alignment = Alignment.CenterVertically,
                        ),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                        value = value,
                        onValueChange = { newText -> onValueChange(newText) },
                        singleLine = true,
                        textStyle =
                            MaterialTheme.typography.bodyLarge.copy(
                                textAlign = TextAlign.Center,
                            ),
                        isError = isError,
                        supportingText = {
                            val isVisible = isError || (state == SetupPinState.SetPin && value.length < 4)
                            val text =
                                if (isError) {
                                    errorMessage
                                } else if (value.length < 4) {
                                    stringResource(id = AppStrings.Supporting.PinMustBeAtLeast4Digits)
                                } else {
                                    ""
                                }
                            val color =
                                if (isError) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }

                            AnimatedVisibility(
                                visible = isVisible,
                                enter = fadeIn(tween(300)),
                                exit = fadeOut(tween(300)),
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = text,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = color,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                            ),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        TextButton(
                            modifier = Modifier,
                            onClick = { onClear() },
                            contentPadding = ButtonDefaults.TextButtonContentPadding,
                        ) {
                            Text(
                                text = stringResource(id = AppStrings.Button.Clear),
                                modifier = Modifier,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                        Button(
                            modifier = Modifier,
                            enabled = value.length >= 4,
                            onClick = { onConfirm() },
                        ) {
                            Text(
                                text =
                                    when (state) {
                                        is SetupPinState.SetPin -> stringResource(id = AppStrings.Button.Next)
                                        is SetupPinState.ConfirmPin -> stringResource(id = AppStrings.Button.Confirm)
                                    },
                                modifier = Modifier,
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            },
            topBar = {
                HNLargeTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onNavigationButtonClick = onNavigationBack,
                    title =
                        when (state) {
                            SetupPinState.SetPin -> stringResource(id = AppStrings.Title.SetAPin)
                            SetupPinState.ConfirmPin -> stringResource(id = AppStrings.Title.ReEnterYourPin)
                        },
                )
            },
        )
    }
}

sealed class SetupPinState {
    object SetPin : SetupPinState()

    object ConfirmPin : SetupPinState()

    fun parse(): String {
        return when (this) {
            SetPin -> SET_PIN
            ConfirmPin -> CONFIRM_PIN
        }
    }

    companion object {
        private const val SET_PIN = "set_pin"
        private const val CONFIRM_PIN = "confirm_pin"

        fun from(s: String?): SetupPinState {
            return when (s) {
                SET_PIN -> SetPin
                CONFIRM_PIN -> ConfirmPin
                else -> SetPin
            }
        }

        fun Saver(): Saver<MutableState<SetupPinState>, *> =
            Saver(
                save = { state ->
                    state.value.parse()
                },
                restore = { state ->
                    mutableStateOf(from(state))
                },
            )
    }
}
