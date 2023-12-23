package com.hellguy39.hellnotes.core.model

sealed class LockRequest {

    data object Force: LockRequest()

    data object Optional: LockRequest()

    fun string(): String {
        return when(this) {
            Force -> FORCE
            Optional -> OPTIONAL
        }
    }

    companion object {

        private const val FORCE = "force"
        private const val OPTIONAL = "optional"

        fun from(s: String?): LockRequest {
            return when(s) {
                FORCE -> Force
                OPTIONAL -> Optional
                else -> Optional
            }
        }
    }

}