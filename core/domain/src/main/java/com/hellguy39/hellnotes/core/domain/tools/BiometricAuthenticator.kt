package com.hellguy39.hellnotes.core.domain.tools

import androidx.fragment.app.FragmentActivity

interface BiometricAuthenticator {
    fun deviceBiometricSupportStatus(): DeviceBiometricStatus

    fun authenticate(activity: FragmentActivity)

    fun setOnBiometricStatusListener(onStatus: (status: DeviceBiometricStatus) -> Unit)

    fun setOnAuthListener(onAuth: (result: AuthenticationResult) -> Unit)
}

sealed interface DeviceBiometricStatus {
    data object Success : DeviceBiometricStatus

    data object NoHardware : DeviceBiometricStatus

    data object HardwareUnavailable : DeviceBiometricStatus

    data object NoneEnrolled : DeviceBiometricStatus

    data object SecurityUpdateRequired : DeviceBiometricStatus

    data object Unsupported : DeviceBiometricStatus

    data object StatusUnknown : DeviceBiometricStatus
}

sealed interface AuthenticationResult {
    data object Success : AuthenticationResult

    data object Failed : AuthenticationResult

    data object Error : AuthenticationResult
}
