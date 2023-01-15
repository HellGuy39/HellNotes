package com.hellguy39.hellnotes.android_features

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.hellguy39.hellnotes.domain.android_system_features.AuthenticationResult
import com.hellguy39.hellnotes.domain.android_system_features.BiometricAuthenticator
import com.hellguy39.hellnotes.domain.android_system_features.DeviceBiometricStatus
import dagger.hilt.android.qualifiers.ApplicationContext

class AndroidBiometricAuthenticator(
    @ApplicationContext private val context: Context
) : BiometricPrompt.AuthenticationCallback(), BiometricAuthenticator {

    private val executor = ContextCompat.getMainExecutor(context)
    private val biometricManager = BiometricManager.from(context)

    private var onAuthListener: (result: AuthenticationResult) -> Unit = {}
    override fun setOnAuthListener(onAuth: (result: AuthenticationResult) -> Unit) {
        onAuthListener = onAuth
    }

    private var onBiometricStatusListener: (status: DeviceBiometricStatus) -> Unit = {}
    override fun setOnBiometricStatusListener(onStatus: (status: DeviceBiometricStatus) -> Unit) {
        onBiometricStatusListener = onStatus
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
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

    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric login for HellNotes")
        .setSubtitle("Log in using your biometric credential")
        .setNegativeButtonText("Use PIN")
        .build()

    override fun authenticate(activity: AppCompatActivity) {

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

        const val AUTHENTICATORS = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL
    }
}