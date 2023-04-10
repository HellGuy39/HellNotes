package com.hellguy39.hellnotes.core.model.util

sealed class LockResult {

    object Success: LockResult()
    object Denied: LockResult()

    fun string(): String {
        return when(this) {
            Success -> SUCCESS
            Denied -> DENIED
        }
    }

    companion object {

        private const val SUCCESS = "success"
        private const val DENIED = "denied"

        fun from(s: String?): LockResult {
            return when(s) {
                SUCCESS -> Success
                DENIED -> Denied
                else -> Denied
            }
        }
    }

}