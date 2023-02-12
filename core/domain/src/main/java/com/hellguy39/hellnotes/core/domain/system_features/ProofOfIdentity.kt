package com.hellguy39.hellnotes.core.domain.system_features

interface ProofOfIdentity {

    fun confirmAppAccess(cancelable: Boolean, onSuccess: () -> Unit)

    fun confirmBiometrics(onSuccess: () -> Unit)

}