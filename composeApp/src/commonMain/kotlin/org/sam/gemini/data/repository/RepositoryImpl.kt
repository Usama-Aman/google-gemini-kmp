package org.sam.gemini.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import org.sam.gemini.common.API_KEY
import org.sam.gemini.common.Constants
import org.sam.gemini.common.NetworkResource
import org.sam.gemini.data.dto.GeminiDTO
import org.sam.gemini.data.remote.KtorClient
import org.sam.gemini.domain.repository.Repository

class RepositoryImpl() : Repository {

    companion object {
        const val TEXT_INPUT_URL = "${Constants.BASE_URL}/gemini-pro:generateContent?key=${API_KEY}}"
    }

    override suspend fun generateContent(content: String): GeminiDTO {

        val requestBody = mapOf(
            "contents" to listOf(
                mapOf(
                    "parts" to listOf(
                        mapOf("text" to content)
                    )
                )
            )
        )

        return KtorClient.getHttpClient().post(TEXT_INPUT_URL) {
            setBody(requestBody)
        }.body()

    }


}