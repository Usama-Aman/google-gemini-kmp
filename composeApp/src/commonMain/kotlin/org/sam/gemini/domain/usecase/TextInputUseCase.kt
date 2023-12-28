package org.sam.gemini.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.sam.gemini.common.NetworkResource
import org.sam.gemini.data.mapper.toResultString
import org.sam.gemini.domain.repository.Repository

class TextInputUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(content: String): Flow<NetworkResource<String>> = flow<NetworkResource<String>> {
        emit(NetworkResource.Loading())

        val result = repository.generateContent(content).toResultString()
        emit(NetworkResource.Success(result))

    }.flowOn(Dispatchers.Unconfined)
        .catch {
            emit(NetworkResource.Error(it.message.toString()))
        }
}