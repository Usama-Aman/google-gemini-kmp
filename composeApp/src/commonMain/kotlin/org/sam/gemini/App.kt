package org.sam.gemini

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.sam.gemini.data.repository.RepositoryImpl
import org.sam.gemini.domain.repository.Repository
import org.sam.gemini.domain.usecase.TextInputUseCase
import org.sam.gemini.presentation.HomeViewModel
import org.sam.gemini.theme.AppTheme
import org.sam.gemini.theme.LocalThemeIsDark

@Composable
internal fun App() = AppTheme {


    val textInputUseCase: TextInputUseCase = remember { TextInputUseCase(RepositoryImpl()) }
    val viewModel = remember { HomeViewModel(textInputUseCase) }
    val state by viewModel.state.collectAsState()

    var generate by remember { mutableStateOf(false) }

    LaunchedEffect(generate) {
        println(generate)
        viewModel.generateContent()
        generate = false
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Gemini KMP",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.weight(1.0f))

            var isDark by LocalThemeIsDark.current
            IconButton(
                onClick = { isDark = !isDark }
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp).size(20.dp),
                    imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = null
                )
            }


        }

        Spacer(modifier = Modifier.size(10.dp))

        if (state.isLoading)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        else
            Column(
                modifier = Modifier.weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = state.resultContent,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                        .fillMaxSize()
                )
            }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            OutlinedTextField(
                modifier = Modifier,
                value = viewModel.content,
                label = { Text(text = "Ask Gemini") },
                onValueChange = viewModel::onContentChanged,
                keyboardActions = KeyboardActions(
                    onDone = { generate = false }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Button(onClick = { generate = true }) {
                Text("Generate")
            }
        }

    }
}

internal expect fun openUrl(url: String?)