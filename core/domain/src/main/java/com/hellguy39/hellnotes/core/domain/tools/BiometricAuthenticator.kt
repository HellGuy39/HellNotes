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
