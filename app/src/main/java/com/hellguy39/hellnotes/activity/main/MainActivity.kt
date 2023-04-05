package com.hellguy39.hellnotes.activity.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hellguy39.hellnotes.android_features.AndroidAlarmScheduler
import com.hellguy39.hellnotes.core.domain.system_features.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.system_features.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.domain.system_features.ProofOfIdentity
import com.hellguy39.hellnotes.core.model.util.LockScreenType
import com.hellguy39.hellnotes.core.ui.system.TransparentSystemBars
import com.hellguy39.hellnotes.feature.lock.LockScreenDialog
import com.hellguy39.hellnotes.navigation.SetupNavGraph
import com.hellguy39.hellnotes.core.ui.theme.HellNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ProofOfIdentity {

    @Inject lateinit var splashViewModel: SplashViewModel

    @Inject lateinit var biometricAuth: BiometricAuthenticator

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.splashState.value.isLoading
        }
        setContent { App() }
    }

    @Composable
    fun App() {
        HellNotesTheme {

            val extraNoteId = intent.extras?.getLong(AndroidAlarmScheduler.ALARM_NOTE_ID, EMPTY_ARG)
            val action = intent.action

            var isIdentityProofed by rememberSaveable { mutableStateOf(false) }
            val splashState by splashViewModel.splashState.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = splashState) {
                if (!splashState.isLoading) {
                    val securityState = splashState.securityState
                    val isOnBoardingCompleted = splashState.onBoardingState

                    if (!isOnBoardingCompleted) {
                        //navigateToWelcomeScreen()
                    }

                    if (securityState.lockType != LockScreenType.None && !isIdentityProofed) {
                        confirmAppAccess(
                            cancelable = false,
                            onSuccess = { isIdentityProofed = true }
                        )
                    }

                    if (securityState.lockType == LockScreenType.None && !isIdentityProofed) {
                        isIdentityProofed = true
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                TransparentSystemBars()
                SetupNavGraph(
                    extraNoteId = extraNoteId,
                    action = action,
                )
            }
        }
    }

    override fun confirmAppAccess(cancelable: Boolean, onSuccess: () -> Unit) {
        val dialog = LockScreenDialog()
        dialog.isCancelable = cancelable
        dialog.setCallback(callback = object: LockScreenDialog.DialogCallback {
            override fun onDismiss() {}
            override fun onSuccess() {
                dialog.dismiss()
                onSuccess()
            }
        })
        dialog.show(supportFragmentManager, "TAG")
    }

    override fun confirmBiometrics(onSuccess: () -> Unit) {
        if (biometricAuth.deviceBiometricSupportStatus() == DeviceBiometricStatus.Success) {
            biometricAuth.setOnAuthListener {
                if (it == AuthenticationResult.Success) {
                    onSuccess()
                }
            }
            biometricAuth.authenticate(this)
        }
    }

    companion object {

        const val EMPTY_ARG = -2L

    }

}