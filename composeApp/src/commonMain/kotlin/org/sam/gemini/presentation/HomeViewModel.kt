package org.sam.gemini.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sam.gemini.common.NetworkResource
import org.sam.gemini.domain.repository.Repository
import org.sam.gemini.domain.usecase.TextInputUseCase

class HomeViewModel(
    private val textInputUseCase: TextInputUseCase
) : ViewModel() {

    data class UIState(
        var isLoading: Boolean = false,
        var resultContent: String = "",
        var errorMessage: String = ""
    )

    var state = MutableStateFlow(UIState())
        private set

    var content by mutableStateOf("")

    fun onContentChanged(text: String) {
        content = text
    }

    suspend fun generateContent() {
        if (content.isBlank())
            return

        textInputUseCase(content).onEach {
            when (it) {
                is NetworkResource.Success -> {
                    state.update { state ->
                        state.copy(
                            isLoading = false,
                            resultContent = it.data.toString()
                        )
                    }
                }

                is NetworkResource.Error -> {
                    state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = it.error.toString()
                        )
                    }
                }

                is NetworkResource.Loading -> {
                    state.update { state ->
                        state.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}