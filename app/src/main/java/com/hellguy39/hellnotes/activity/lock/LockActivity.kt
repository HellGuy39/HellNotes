package com.hellguy39.hellnotes.activity.lock

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.hellguy39.hellnotes.activity.main.MainActivity
import com.hellguy39.hellnotes.android_features.AndroidBiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.ui.resources.HellNotesIcons
import com.hellguy39.hellnotes.core.ui.system.TransparentSystemBars
import com.hellguy39.hellnotes.ui.theme.HellNotesTheme
import com.hellguy39.hellnotes.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class LockActivity : AppCompatActivity() {

    private val lockViewModel by viewModels<LockViewModel>()

    private val bioNoneEnrolledLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
    }

    @Inject lateinit var biometricAuth: BiometricAuthenticator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }

        if (!lockViewModel.isAppLocked()) {
            navigateToMainActivity()
        }

        setContent {
            HellNotesTheme {

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransparentSystemBars()
                    LockScreenContent(
                        lockViewModel = lockViewModel,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                }

                biometricAuth.setOnAuthListener { result ->
                    when (result) {
                        is AuthenticationResult.Success -> {
                            lockViewModel.authByBiometric()
                            navigateToMainActivity()
                        }
                        is AuthenticationResult.Failed -> Unit
                        is AuthenticationResult.Error -> Unit
                    }
                }

                if (lockViewModel.isUseBiometric()) {
                    authByBiometric()
                }
            }
        }
    }

    private fun authByBiometric() {
        when (biometricAuth.deviceBiometricSupportStatus()) {
            DeviceBiometricStatus.Success -> {
                biometricAuth.authenticate(this)
            }
            DeviceBiometricStatus.NoHardware -> {
                showToast("No hardware")
            }
            DeviceBiometricStatus.Unsupported -> {
                showToast("Unsupported")
            }
            DeviceBiometricStatus.HardwareUnavailable -> {
                showToast("Hardware unavailable")
            }
            DeviceBiometricStatus.NoneEnrolled -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            AndroidBiometricAuthenticator.AUTHENTICATORS
                        )
                    }
                    bioNoneEnrolledLauncher.launch(enrollIntent)
                } else {
                    showToast("Biometric none enrolled")
                }
            }
            DeviceBiometricStatus.SecurityUpdateRequired -> {
                showToast("Security update required")
            }
            DeviceBiometricStatus.StatusUnknown -> {
                showToast("Status unknown")
            }
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LockScreenContent(
        lockViewModel: LockViewModel,
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {
        val uiState by lockViewModel.uiState.collectAsState()
        val pin by lockViewModel.pin.collectAsState()

        val hapticFeedback = LocalHapticFeedback.current

        if (uiState is LockUiState.Unlocked) {
            navigateToMainActivity()
        }

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

                    val titleIcon = when (uiState) {
                        is LockUiState.Unlocked -> painterResource(id = HellNotesIcons.LockOpen)
                        else -> painterResource(id = HellNotesIcons.Lock)
                    }

                    val titleText = when (uiState) {
                        is LockUiState.Locked -> "Enter your PIN"
                        is LockUiState.Unlocked -> "Unlocked"
                        is LockUiState.WrongPin -> "Wrong PIN"
                    }

                    Icon(
                        painter = titleIcon,
                        contentDescription = null
                    )

                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    PinDots(
                        pin = pin
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    NumberKeyboard(
                        onClick = { key ->
                            if (key == KEY_BIO) {
                                authByBiometric()
                            } else {
                                lockViewModel.enterKey(key)
                            }
                        },
                        onLongClick = { key ->
                            if (key == KEY_BACKSPACE) {
                                lockViewModel.clearPin()
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                        }
                    )
                }
            }
        )
    }

    @Composable
    fun PinDots(pin: String) {

        val count = pin.length
        val isFirstEntered = count >= 1
        val isSecondEntered = count >= 2
        val isThirdEntered = count >= 3
        val isFourthEntered = count >= 4

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isFirstEntered)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isSecondEntered)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isThirdEntered)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isFourthEntered)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )
        }
    }

    @Composable
    fun NumberKeyboard(
        onClick: (key: String) -> Unit,
        onLongClick: (key: String) -> Unit
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            KeyboardNumberButton(key = KEY_1, onClick = onClick)
            KeyboardNumberButton(key = KEY_2, onClick = onClick)
            KeyboardNumberButton(key = KEY_3, onClick = onClick)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            KeyboardNumberButton(key = KEY_4, onClick = onClick)
            KeyboardNumberButton(key = KEY_5, onClick = onClick)
            KeyboardNumberButton(key = KEY_6, onClick = onClick)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            KeyboardNumberButton(key = KEY_7, onClick = onClick)
            KeyboardNumberButton(key = KEY_8, onClick = onClick)
            KeyboardNumberButton(key = KEY_9, onClick = onClick)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            KeyboardNumberButton(key = KEY_BIO, onClick = onClick)
            KeyboardNumberButton(key = KEY_0, onClick = onClick)
            KeyboardNumberButton(key = KEY_BACKSPACE, onClick = onClick, onLongClick = onLongClick)
        }
        Spacer(modifier = Modifier.size(width = 0.dp, height = 16.dp))
//        Button(
//            onClick = { },
//            contentPadding = ButtonDefaults.ButtonWithIconContentPadding
//        ) {
//            Icon(
//                painter = painterResource(id = HellNotesIcons.Fingerprint),
//                contentDescription = null,
//                modifier = Modifier.size(ButtonDefaults.IconSize)
//            )
//            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
//            Text(text = "Use biometric")
//        }
    }

    @Composable
    fun KeyboardNumberButton(
        key: String = "",
        onClick: (key: String) -> Unit,
        onLongClick: (key: String) -> Unit = {}
    ) {
        when (key) {
            KEY_BACKSPACE -> {
                FilledIconButton(
                    onClick = { onClick(key) },
                    modifier = Modifier.size(width = 84.dp, height = 84.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Backspace),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            KEY_BIO -> {
                FilledIconButton(
                    onClick = { onClick(key) },
                    modifier = Modifier.size(width = 84.dp, height = 84.dp),
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = HellNotesIcons.Fingerprint),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            else -> {
                FilledTonalIconButton(
                    onClick = { onClick(key) },
                    modifier = Modifier.size(width = 84.dp, height = 84.dp),
                    shape = CircleShape
                ) {
                    Text(
                        text = key,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }

    companion object {

        const val KEY_1 = "1"
        const val KEY_2 = "2"
        const val KEY_3 = "3"
        const val KEY_4 = "4"
        const val KEY_5 = "5"
        const val KEY_6 = "6"
        const val KEY_7 = "7"
        const val KEY_8 = "8"
        const val KEY_9 = "9"
        const val KEY_0 = "0"
        const val KEY_BACKSPACE = "backspace"
        const val KEY_BIO = "bio"
    }
}