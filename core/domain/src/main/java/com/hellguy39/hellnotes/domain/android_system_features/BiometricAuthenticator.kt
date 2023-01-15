package com.hellguy39.hellnotes.domain.android_system_features

import androidx.appcompat.app.AppCompatActivity

interface BiometricAuthenticator {
    fun deviceBiometricSupportStatus(): DeviceBiometricStatus
    fun authenticate(activity: AppCompatActivity)
    fun setOnBiometricStatusListener(onStatus: (status: DeviceBiometricStatus) -> Unit)
    fun setOnAuthListener(onAuth: (result: AuthenticationResult) -> Unit)
}

sealed interface DeviceBiometricStatus {
    object Success : DeviceBiometricStatus
    object NoHardware : DeviceBiometricStatus
    object HardwareUnavailable : DeviceBiometricStatus
    object NoneEnrolled : DeviceBiometricStatus
    object SecurityUpdateRequired : DeviceBiometricStatus
    object Unsupported : DeviceBiometricStatus
    object StatusUnknown : DeviceBiometricStatus
}

sealed interface AuthenticationResult {
    object Success : AuthenticationResult
    object Failed : AuthenticationResult
    object Error : AuthenticationResult
}
