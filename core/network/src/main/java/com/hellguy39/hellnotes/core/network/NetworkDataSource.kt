package com.hellguy39.hellnotes.core.network

import com.hellguy39.hellnotes.core.network.dto.ReleaseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class NetworkDataSource
    @Inject
    constructor(
        private val client: HttpClient,
    ) {
        suspend fun getReleases(): List<ReleaseDto> {
            val response =
                client.get {
                    url(HttpRoutes.RELEASES)
                    contentType(ContentType.Application.Json)
                }
            return response.body()
        }

        suspend fun getPrivacyPolicy(): String {
            val response =
                client.get {
                    url(HttpRoutes.PRIVACY_POLICY)
                }
            return response.body()
        }

        suspend fun getTermsAndConditions(): String {
            val response =
                client.get {
                    url(HttpRoutes.TERMS_AND_CONDITIONS)
                }
            return response.body()
        }
    }
