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
package com.hellguy39.hellnotes.tools

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.hellguy39.hellnotes.core.domain.tools.AuthenticationResult
import com.hellguy39.hellnotes.core.domain.tools.BiometricAuthenticator
import com.hellguy39.hellnotes.core.domain.tools.DeviceBiometricStatus
import com.hellguy39.hellnotes.core.ui.resources.AppStrings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BiometricAuthenticatorImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : BiometricPrompt.AuthenticationCallback(),
        BiometricAuthenticator {
        private val executor by lazy { ContextCompat.getMainExecutor(context) }
        private val biometricManager by lazy { BiometricManager.from(context) }

        private var onAuthListener: (result: AuthenticationResult) -> Unit = {}

        override fun setOnAuthListener(onAuth: (result: AuthenticationResult) -> Unit) {
            onAuthListener = onAuth
        }

        private var onBiometricStatusListener: (status: DeviceBiometricStatus) -> Unit = {}

        override fun setOnBiometricStatusListener(onStatus: (status: DeviceBiometricStatus) -> Unit) {
            onBiometricStatusListener = onStatus
        }

        override fun onAuthenticationError(
            errorCode: Int,
            errString: CharSequence,
        ) {
            super.onAuthenticationError(errorCode, errString)
            onAuthListener.invoke(AuthenticationResult.Error)
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            onAuthListener.invoke(AuthenticationResult.Failed)
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onAuthListener.invoke(AuthenticationResult.Success)
        }

        override fun authenticate(activity: FragmentActivity) {
            val promptInfo =
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle(context.getString(AppStrings.Title.AuthRequired))
                    .setSubtitle(context.getString(AppStrings.AppName))
                    .setNegativeButtonText(context.getString(AppStrings.Button.Cancel))
                    .build()

            val biometricPrompt = BiometricPrompt(activity, executor, this)

            biometricPrompt.authenticate(promptInfo)
        }

        override fun deviceBiometricSupportStatus(): DeviceBiometricStatus {
            return when (biometricManager.canAuthenticate(AUTHENTICATORS)) {
                BiometricManager.BIOMETRIC_SUCCESS -> DeviceBiometricStatus.Success

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> DeviceBiometricStatus.NoHardware

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    DeviceBiometricStatus.HardwareUnavailable

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> DeviceBiometricStatus.NoneEnrolled

                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED ->
                    DeviceBiometricStatus.SecurityUpdateRequired

                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> DeviceBiometricStatus.Unsupported

                BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> DeviceBiometricStatus.StatusUnknown

                else -> DeviceBiometricStatus.StatusUnknown
            }
        }

        companion object {
            const val AUTHENTICATORS =
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        }
    }
