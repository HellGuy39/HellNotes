package com.hellguy39.hellnotes.core.network.di

import com.hellguy39.hellnotes.core.network.util.KtorLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.INFO
                logger = KtorLogger()
            }
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
    }
}