package com.hellguy39.hellnotes.core.network.util

import android.util.Log
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

private const val TAG = "NetworkExceptionHandler"

fun handleException(cause: Throwable): String {
    return when (cause) {
        // 3xx - responses
        is RedirectResponseException -> {
            val errorMessage = cause.response.status.description
            Log.e(TAG, errorMessage)
            errorMessage
        }
        // 4xx - responses
        is ClientRequestException -> {
            val errorMessage = cause.response.status.description
            Log.e(TAG, errorMessage)
            errorMessage
        }
        // 5xx - responses
        is ServerResponseException -> {
            val errorMessage = cause.response.status.description
            Log.e(TAG, errorMessage)
            errorMessage
        }
        else -> {
            val errorMessage = cause.message.toString()
            Log.e(TAG, errorMessage)
            errorMessage
        }
    }
}