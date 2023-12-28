package org.sam.gemini.common

// This is correct and more efficient
sealed class NetworkResource<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : NetworkResource<T>(data = data)
    class Error<T>(error: String? = null) : NetworkResource<T>(error = error)
    class Loading<T>() : NetworkResource<T>()
}