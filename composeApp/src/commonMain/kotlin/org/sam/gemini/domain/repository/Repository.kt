package org.sam.gemini.domain.repository

import org.sam.gemini.common.NetworkResource
import org.sam.gemini.data.dto.GeminiDTO

interface Repository {
    suspend fun generateContent(content : String): GeminiDTO
}