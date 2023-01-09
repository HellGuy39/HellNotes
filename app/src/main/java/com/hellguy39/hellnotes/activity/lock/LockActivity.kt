package com.hellguy39.hellnotes.activity.lock

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hellguy39.hellnotes.activity.main.MainActivity
import com.hellguy39.hellnotes.resources.HellNotesIcons
import com.hellguy39.hellnotes.ui.theme.HellNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockActivity : AppCompatActivity() {

    private val lockViewModel by viewModels<LockViewModel>()

    private var biometricHelper: BiometricHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }
        setContent { LockScreen() }

        if (!lockViewModel.isAppLocked()) {
            navigateToMainActivity()
        } else {
            biometricHelper = BiometricHelper(this)

            biometricHelper?.setOnSuccessListener {
                lockViewModel.authByBiometric()
                navigateToMainActivity()
            }

            if (lockViewModel.isUseBiometric()) {
                biometricHelper?.authenticate()
            }
        }
    }

    inner class BiometricHelper(
        activity: FragmentActivity
    ) : BiometricPrompt.AuthenticationCallback() {

        private val executor = ContextCompat.getMainExecutor(activity)
        private val biometricPrompt = BiometricPrompt(activity, executor, this)

        private var onSuccessListener: () -> Unit = {}

        fun setOnSuccessListener(onSuccess: () -> Unit) {
            onSuccessListener = onSuccess
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(
                applicationContext,
                "Authentication error: $errString",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(
                applicationContext, "Authentication failed",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            Toast.makeText(
                applicationContext,
                "Authentication succeeded!", Toast.LENGTH_SHORT
            ).show()
            onSuccessListener.invoke()
        }

        private val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for HellNotes")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use PIN")
            .build()

        fun authenticate() {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @Composable
    fun LockScreen() {
        HellNotesTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TransparentSystemBars()
                LockScreenContent(lockViewModel)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LockScreenContent(
        lockViewModel: LockViewModel
    ) {
        val uiState by lockViewModel.uiState.collectAsState()
        val pin by lockViewModel.pin.collectAsState()

        val hapticFeedback = LocalHapticFeedback.current

        if (uiState is LockUiState.Unlocked) {
            navigateToMainActivity()
        }

        Scaffold(
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
                                biometricHelper?.authenticate()
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
    fun TransparentSystemBars() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        val color = MaterialTheme.colorScheme.background
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons,
                isNavigationBarContrastEnforced = false,
                transformColorForLightContent = { original ->
                    color.compositeOver(original)
                }
            )
        }
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

    @OptIn(ExperimentalFoundationApi::class)
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

//    fun checkDeviceHasBiometric() {
//        val biometricManager = BiometricManager.from(this)
//        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
//            BiometricManager.BIOMETRIC_SUCCESS -> {
//                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
//
//            }
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//                Log.e("MY_APP_TAG", "No biometric features available on this device.")
//            }
//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
//
//            }
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//                // Prompts the user to create credentials that your app accepts.
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                        putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
//                    }
//                    startActivityForResult(enrollIntent, 100)
//                }
//            }
//            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
//                TODO()
//            }
//            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
//                TODO()
//            }
//            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
//                TODO()
//            }
//        }
//    }
}