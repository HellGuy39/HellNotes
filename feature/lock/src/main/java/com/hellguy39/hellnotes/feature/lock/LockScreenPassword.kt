package com.hellguy39.hellnotes.feature.lock

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings
import com.hellguy39.hellnotes.core.ui.values.slideContentTransform

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LockScreenPassword(
    paddingValues: PaddingValues,
    uiState: LockUiState,
    onBiometricsAuth: () -> Unit,
    passwordSelection: PasswordSelection,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier =
            Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Bottom,
            ),
    ) {
        val titleIcon =
            when (uiState.lockState) {
                is LockState.Unlocked -> painterResource(id = HellNotesIcons.LockOpen)
                else -> painterResource(id = HellNotesIcons.Lock)
            }

        val titleText =
            when (uiState.lockState) {
                is LockState.Locked -> stringResource(id = HellNotesStrings.Title.EnterPassword)
                is LockState.Unlocked -> stringResource(id = HellNotesStrings.Title.Unlocked)
                is LockState.WrongPin -> stringResource(id = HellNotesStrings.Supporting.WrongPassword)
            }

        Icon(
            painter = titleIcon,
            contentDescription = null,
        )

        AnimatedContent(
            targetState = titleText,
            transitionSpec = { slideContentTransform() },
            label = "slideContentTransform",
        ) { targetTitle ->
            Text(
                text = targetTitle,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = uiState.password,
            onValueChange = passwordSelection.onValueChange,
            textStyle = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Center),
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
                modifier = Modifier.width(96.dp),
                onClick = passwordSelection.onClear,
                contentPadding = ButtonDefaults.TextButtonContentPadding,
            ) {
                Text(
                    text = stringResource(id = HellNotesStrings.Button.Clear),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            AssistChip(
                onClick = onBiometricsAuth,
                enabled = uiState.securityState.isUseBiometricData,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        painter = painterResource(id = HellNotesIcons.Fingerprint),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Biometrics),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                shape = RoundedCornerShape(16.dp),
            )
            Button(
                modifier = Modifier.width(96.dp),
                enabled = uiState.password.length >= 4,
                onClick = passwordSelection.onEntered,
            ) {
                Text(
                    text = stringResource(id = HellNotesStrings.Button.Enter),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

data class PasswordSelection(
    val onClear: () -> Unit,
    val onEntered: () -> Unit,
    val onValueChange: (text: String) -> Unit,
)
