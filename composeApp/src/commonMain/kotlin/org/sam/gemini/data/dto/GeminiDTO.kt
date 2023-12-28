package org.sam.gemini.data.dto
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName



@Serializable
data class GeminiDTO(
    @SerialName("candidates")
    var candidates: List<Candidate>? = listOf(),
    @SerialName("promptFeedback")
    var promptFeedback: PromptFeedback? = PromptFeedback()
)

@Serializable
data class Candidate(
    @SerialName("content")
    var content: Content? = null,
    @SerialName("finishReason")
    var finishReason: String? = null,
    @SerialName("index")
    var index: Int? = null,
    @SerialName("safetyRatings")
    var safetyRatings: List<SafetyRating?>? = null
)

@Serializable
data class PromptFeedback(
    @SerialName("safetyRatings")
    var safetyRatings: List<SafetyRating>? = listOf()
)

@Serializable
data class Content(
    @SerialName("parts")
    var parts: List<Part?>? = null,
    @SerialName("role")
    var role: String? = null
)

@Serializable
data class SafetyRating(
    @SerialName("category")
    var category: String? = null,
    @SerialName("probability")
    var probability: String? = null
)

@Serializable
data class Part(
    @SerialName("text")
    var text: String? = null
)