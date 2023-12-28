package org.sam.gemini.data.mapper

import org.sam.gemini.data.dto.GeminiDTO

fun GeminiDTO.toResultString(): String {
    return candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: ""
}