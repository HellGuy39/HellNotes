package com.hellguy39.hellnotes.feature.lock

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.resources.HellNotesStrings

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LockScreenPassword(
    paddingValues: PaddingValues,
    uiState: LockUiState,
    onBiometricsAuth: () -> Unit,
    passwordSelection: PasswordSelection,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
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
            is LockState.Locked -> stringResource(id = HellNotesStrings.Helper.EnterPin)
            is LockState.Unlocked -> stringResource(id = HellNotesStrings.Helper.Unlocked)
            is LockState.WrongPin -> stringResource(id = HellNotesStrings.Helper.WrongPin)
        }

        Icon(
            painter = titleIcon,
            contentDescription = null
        )

        val duration = 200

        AnimatedContent(
            targetState = titleText,
            transitionSpec = {
                slideInHorizontally(animationSpec = tween(duration)) { fullWidth -> fullWidth } + fadeIn(animationSpec = tween(duration)) with
                        slideOutHorizontally(animationSpec = tween(duration)) { fullWidth -> -fullWidth } + fadeOut(animationSpec = tween(duration))
            }
        ) { targetTitle ->
            Text(
                text = targetTitle,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            modifier = Modifier,
            value = uiState.password,
            onValueChange = passwordSelection.onValueChange,
            textStyle = MaterialTheme.typography.displaySmall.copy(textAlign = TextAlign.Center),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                modifier = Modifier,
                onClick = passwordSelection.onClear,
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text(
                    text = stringResource(id = HellNotesStrings.Button.Clear),
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            AssistChip(
                onClick = onBiometricsAuth,
                enabled = uiState.securityState.isUseBiometricData,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        painter = painterResource(id = HellNotesIcons.Fingerprint),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = HellNotesStrings.Button.Biometrics),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                shape = RoundedCornerShape(16.dp)
            )
            Button(
                modifier = Modifier,
                enabled = uiState.password.length >= 4,
                onClick = passwordSelection.onEntered,
            ) {
                Text(
                    text = "Enter",
                    modifier = Modifier,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

data class PasswordSelection(
    val onClear: () -> Unit,
    val onEntered: () -> Unit,
    val onValueChange: (text: String) -> Unit
)