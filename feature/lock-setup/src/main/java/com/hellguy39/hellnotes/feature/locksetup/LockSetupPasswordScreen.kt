package com.hellguy39.hellnotes.feature.locksetup

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun LockSetupPasswordScreen(
    onNavigationBack: () -> Unit,
    onPasswordEntered: (String) -> Unit,
) {
    val context = LocalContext.current

    var value by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable(saver = SetupPasswordState.Saver()) { mutableStateOf(SetupPasswordState.SetPassword) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)

    fun onValueChange(newText: String) {
        value = newText
        isError = false
    }

    fun onConfirm() {
        if (value.length >= 4) {
            when (state) {
                SetupPasswordState.SetPassword -> {
                    password = value
                    state = SetupPasswordState.ConfirmPassword
                }
                SetupPasswordState.ConfirmPassword -> {
                    if (password == value) {
                        onPasswordEntered(password)
                    } else {
                        isError = true
                        errorMessage = context.getString(AppStrings.Supporting.WrongPassword)
                    }
                }
            }
            value = ""
        }
    }

    fun onClear() {
        value = ""
    }

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
                            .padding(horizontal = 16.dp)
                            .padding(top = paddingValues.calculateTopPadding())
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
                            val isVisible = isError || (state == SetupPasswordState.SetPassword && value.length < 4)
                            val text =
                                if (isError) {
                                    errorMessage
                                } else if (value.length < 4) {
                                    stringResource(id = AppStrings.Supporting.PasswordMustBeAtLeast4Characters)
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
                                keyboardType = KeyboardType.Password,
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
                                        is SetupPasswordState.SetPassword -> stringResource(id = AppStrings.Button.Next)
                                        is SetupPasswordState.ConfirmPassword -> stringResource(id = AppStrings.Button.Confirm)
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
                            SetupPasswordState.SetPassword -> stringResource(id = AppStrings.Title.SetAPassword)
                            SetupPasswordState.ConfirmPassword -> stringResource(id = AppStrings.Title.ReEnterYourPassword)
                        },
                )
            },
        )
    }
}

sealed class SetupPasswordState {
    object SetPassword : SetupPasswordState()

    object ConfirmPassword : SetupPasswordState()

    fun parse(): String {
        return when (this) {
            SetPassword -> SET_PASSWORD
            ConfirmPassword -> CONFIRM_PASSWORD
        }
    }

    companion object {
        private const val SET_PASSWORD = "set_password"
        private const val CONFIRM_PASSWORD = "confirm_passsword"

        fun from(s: String?): SetupPasswordState {
            return when (s) {
                SET_PASSWORD -> SetPassword
                CONFIRM_PASSWORD -> ConfirmPassword
                else -> SetPassword
            }
        }

        fun Saver(): Saver<MutableState<SetupPasswordState>, *> =
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
