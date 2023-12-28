package org.sam.gemini.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.sam.gemini.common.Constants

object KtorClient {

    private const val TIME_OUT = 300_000L

    private val httpClient: HttpClient? = null

    fun getHttpClient(): HttpClient {
        return httpClient
            ?: HttpClient() {

                install(DefaultRequest) {
                    accept(ContentType.Application.Json)
                    contentType(ContentType.Application.Json)
                }

                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("HttpClient - $message")
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                        encodeDefaults = true
                    })
                }

                install(HttpTimeout) {
                    connectTimeoutMillis = TIME_OUT
                    requestTimeoutMillis = TIME_OUT
                    socketTimeoutMillis = TIME_OUT
                }
            }
    }

}