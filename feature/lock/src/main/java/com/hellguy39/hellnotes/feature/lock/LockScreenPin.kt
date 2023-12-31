package com.hellguy39.hellnotes.feature.lock

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.components.input.HNClearTextField
import com.hellguy39.hellnotes.core.ui.components.input.HNVirtualNumberKeyboard
import com.hellguy39.hellnotes.core.ui.components.input.NumberKeyboardSelection
import com.hellguy39.hellnotes.core.ui.resources.AppIcons
import com.hellguy39.hellnotes.core.ui.resources.AppStrings

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LockScreenPin(
    paddingValues: PaddingValues,
    uiState: LockUiState,
    numberKeyboardSelection: NumberKeyboardSelection,
    onBiometricsAuth: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 16.dp),
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
                is LockState.Locked -> stringResource(id = AppStrings.Title.EnterPin)
                is LockState.Unlocked -> stringResource(id = AppStrings.Title.Unlocked)
                is LockState.WrongPin -> stringResource(id = AppStrings.Supporting.WrongPin)
            }

        Icon(
            painter = titleIcon,
            contentDescription = null,
        )

        val duration = 200

        AnimatedContent(
            targetState = titleText,
            transitionSpec = {
                (
                    slideInVertically(
                        animationSpec = tween(duration),
                    ) { fullWidth -> fullWidth } + fadeIn(animationSpec = tween(duration))
                )
                    .togetherWith(
                        slideOutVertically(
                            animationSpec = tween(duration),
                        ) { fullWidth -> -fullWidth } + fadeOut(animationSpec = tween(duration)),
                    )
            },
            label = "",
        ) { targetTitle ->
            Text(
                text = targetTitle,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HNClearTextField(
            value = uiState.password,
            onValueChange = {},
            readOnly = true,
            textStyle = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Center),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                ),
        )

        Spacer(modifier = Modifier.weight(1f))

        HNVirtualNumberKeyboard(
            selection = numberKeyboardSelection,
            disabledButtonKeys = listOf(),
        )

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
    }
}
