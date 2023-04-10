package com.hellguy39.hellnotes.core.model.util

sealed class LockRequest {
    object Force: LockRequest()
    object Optional: LockRequest()

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