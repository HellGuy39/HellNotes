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

    suspend fun getReleases(
        onSuccess: suspend (releasesDto: List<ReleaseDto>) -> Unit,
        onException: suspend (message: String) -> Unit
    ) {
        withExceptions(
            block = {
                val response = client.get { url(HttpRoutes.RELEASES) }
                val releasesDto = response.body<List<ReleaseDto>>()
                onSuccess(releasesDto)
            },
            onException = onException
        )
    }

    suspend fun getPrivacyPolicy(
        onSuccess: suspend (privacyPolicy: String) -> Unit,
        onException: suspend (message: String) -> Unit
    ) {
        withExceptions(
            block = {
                val response = client.get { url(HttpRoutes.PRIVACY_POLICY) }
                val privacyPolicy = response.body<String>()
                onSuccess(privacyPolicy)
            },
            onException = onException
        )
    }

    suspend fun getTermsAndConditions(
        onSuccess: suspend (termsAndConditions: String) -> Unit,
        onException: suspend (message: String) -> Unit
    ) {
        withExceptions(
            block = {
                val response = client.get { url(HttpRoutes.TERMS_AND_CONDITIONS) }
                val termsAndConditions = response.body<String>()
                onSuccess(termsAndConditions)
            },
            onException = onException
        )
    }

    companion object {
        const val TAG = "Network Data Source"
    }

}

private suspend fun withExceptions(
    block: suspend () -> Unit,
    onException: suspend (message: String) -> Unit
) {
    try {
        block()
    } catch (e: RedirectResponseException) {
        // 3xx - responses
        val errorMessage = e.response.status.description
        Log.d(NetworkDataSource.TAG, errorMessage)
        onException(errorMessage)
    } catch (e: ClientRequestException) {
        // 4xx - responses
        val errorMessage = e.response.status.description
        Log.d(NetworkDataSource.TAG, errorMessage)
        onException(errorMessage)
    } catch (e: ServerResponseException) {
        // 5xx - responses
        val errorMessage = e.response.status.description
        Log.d(NetworkDataSource.TAG, errorMessage)
        onException(errorMessage)
    } catch (e: Exception) {
        val errorMessage =e.message.toString()
        Log.d(NetworkDataSource.TAG, errorMessage)
        onException(errorMessage)
    }
}