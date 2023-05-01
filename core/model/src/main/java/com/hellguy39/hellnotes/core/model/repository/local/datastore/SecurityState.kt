package com.hellguy39.hellnotes.core.model.repository.local.datastore

import com.hellguy39.hellnotes.core.model.LockScreenType

data class SecurityState(
    val lockType: LockScreenType,
    val password: String,
    val isUseBiometricData: Boolean,
) {
    companion object {
        fun initialInstance() = SecurityState(
            lockType = LockScreenType.None,
            password = "initial_instance",
            isUseBiometricData = false,
        )
    }
}
