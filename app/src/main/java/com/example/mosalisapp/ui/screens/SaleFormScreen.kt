package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.components.BusinessButton
import com.example.mosalisapp.ui.components.BusinessTextField
import com.example.mosalisapp.ui.viewmodel.WorkerUiState
import com.example.mosalisapp.ui.viewmodel.WorkerViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleFormScreen(
    onNavigateBack: () -> Unit,
    viewModel: WorkerViewModel = koinViewModel()
) {
    var productId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is WorkerUiState.Success) {
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nouvelle Vente") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BusinessTextField(value = productId, onValueChange = { productId = it }, label = "ID Produit")
            BusinessTextField(value = quantity, onValueChange = { quantity = it }, label = "Quantité")
            BusinessTextField(value = amount, onValueChange = { amount = it }, label = "Montant Total ($)")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (uiState is WorkerUiState.Loading) {
                CircularProgressIndicator()
            } else {
                BusinessButton(
                    text = "Valider la vente",
                    onClick = {
                        viewModel.createSale(productId, quantity.toIntOrNull() ?: 0, amount.toDoubleOrNull() ?: 0.0)
                    }
                )
            }
        }
    }
}
