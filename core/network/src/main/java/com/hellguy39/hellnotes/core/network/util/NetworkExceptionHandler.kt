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
