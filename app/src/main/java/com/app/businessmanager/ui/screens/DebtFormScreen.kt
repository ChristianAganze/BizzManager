package com.app.businessmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.businessmanager.ui.components.BusinessButton
import com.app.businessmanager.ui.components.BusinessTextField
import com.app.businessmanager.ui.viewmodel.WorkerUiState
import com.app.businessmanager.ui.viewmodel.WorkerViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtFormScreen(
    navController: NavController,
    viewModel: WorkerViewModel = koinViewModel()
) {
    var clientId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is WorkerUiState.Success) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nouvelle Dette") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BusinessTextField(value = clientId, onValueChange = { clientId = it }, label = "ID Client")
            BusinessTextField(value = amount, onValueChange = { amount = it }, label = "Montant ($)")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (uiState is WorkerUiState.Loading) {
                CircularProgressIndicator()
            } else {
                BusinessButton(
                    text = "Enregistrer la dette",
                    onClick = {
                        viewModel.createDebt(clientId, amount.toDoubleOrNull() ?: 0.0)
                    }
                )
            }
        }
    }
}
