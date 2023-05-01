package com.hellguy39.hellnotes.core.network

import android.util.Log
import com.hellguy39.hellnotes.core.network.dto.ReleaseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Inject

class NetworkDataSource @Inject constructor() {

    private val client = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) { json() }
    }

    suspend fun getReleases(): List<ReleaseDto> {
        val response = client.get { url(HttpRoutes.RELEASES) }
        return response.body()
    }

    suspend fun getPrivacyPolicy(): String {
        val response = client.get { url(HttpRoutes.PRIVACY_POLICY) }
        return response.body<String>()
    }

    suspend fun getTermsAndConditions(): String {
        val response = client.get { url(HttpRoutes.TERMS_AND_CONDITIONS) }
        return response.body<String>()
    }

    companion object {
        const val TAG = "Network Data Source"
    }

}

fun handleException(cause: Throwable): String {
    return when (cause) {
        is RedirectResponseException -> {
            // 3xx - responses
            val errorMessage = cause.response.status.description
            Log.d(NetworkDataSource.TAG, errorMessage)
            errorMessage
        }
        is ClientRequestException -> {
            // 4xx - responses
            val errorMessage = cause.response.status.description
            Log.d(NetworkDataSource.TAG, errorMessage)
            errorMessage
        }
        is ServerResponseException -> {
            // 5xx - responses
            val errorMessage = cause.response.status.description
            Log.d(NetworkDataSource.TAG, errorMessage)
            errorMessage
        }
        else -> {
            val errorMessage = cause.message.toString()
            Log.d(NetworkDataSource.TAG, errorMessage)
            errorMessage
        }
    }
}