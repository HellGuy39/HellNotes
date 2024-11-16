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
import com.hellguy39.hellnotes.core.ui.animations.slideContentTransform
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

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
                is LockState.Unlocked -> painterResource(id = AppIcons.LockOpen)
                else -> painterResource(id = AppIcons.Lock)
            }

        val titleText =
            when (uiState.lockState) {
                is LockState.Locked -> stringResource(id = AppStrings.Title.EnterPassword)
                is LockState.Unlocked -> stringResource(id = AppStrings.Title.Unlocked)
                is LockState.WrongPin -> stringResource(id = AppStrings.Supporting.WrongPassword)
                else -> ""
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
                    text = stringResource(id = AppStrings.Button.Clear),
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
                        painter = painterResource(id = AppIcons.Fingerprint),
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = AppStrings.Button.Biometrics),
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
                    text = stringResource(id = AppStrings.Button.Enter),
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
