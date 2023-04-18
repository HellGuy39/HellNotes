package com.hellguy39.hellnotes.core.network

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
            level = LogLevel.INFO
        }
        install(ContentNegotiation) { json() }
    }

    suspend fun getReleases(): List<ReleaseDto> {
        return try {
            client.get {
                url(HttpRoutes.RELEASES)
            }.body()
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            //e.response.status.description
            emptyList()
        } catch (e: ClientRequestException) {
            // 4xx - responses
            emptyList()
        } catch (e: ServerResponseException) {
            // 5xx - responses
            emptyList()
        }
    }

}