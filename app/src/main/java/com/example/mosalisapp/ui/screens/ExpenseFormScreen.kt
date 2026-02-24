package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.components.BusinessButton
import com.example.mosalisapp.ui.components.BusinessTextField
import com.example.mosalisapp.ui.viewmodel.WorkerUiState
import com.example.mosalisapp.ui.viewmodel.WorkerViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseFormScreen(
    onNavigateBack: () -> Unit,
    viewModel: WorkerViewModel = koinViewModel()
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is WorkerUiState.Success) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nouvelle Dépense") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Enregistrer une dépense", style = MaterialTheme.typography.titleLarge)
            
            BusinessTextField(
                value = title,
                onValueChange = { title = it },
                label = "Titre de la dépense"
            )
            
            BusinessTextField(
                value = amount,
                onValueChange = { amount = it },
                label = "Montant ($)"
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (uiState is WorkerUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                BusinessButton(
                    text = "Enregistrer la dépense",
                    onClick = {
                        viewModel.createExpense(
                            title = title,
                            amount = amount.toDoubleOrNull() ?: 0.0
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
