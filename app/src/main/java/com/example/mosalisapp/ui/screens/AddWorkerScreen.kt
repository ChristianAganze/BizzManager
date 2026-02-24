package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.components.BusinessButton
import com.example.mosalisapp.ui.components.BusinessTextField
import com.example.mosalisapp.ui.viewmodel.WorkerManagementViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkerScreen(
    onNavigateBack: () -> Unit,
    viewModel: WorkerManagementViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ajouter un Employé") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BusinessTextField(value = name, onValueChange = { name = it }, label = "Nom complet")
            BusinessTextField(value = email, onValueChange = { email = it }, label = "Email")
            BusinessTextField(value = password, onValueChange = { password = it }, label = "Mot de passe initial")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            BusinessButton(
                text = "Enregistrer l'employé",
                onClick = {
                    viewModel.addWorker(name, email, password)
                    onNavigateBack()
                }
            )
        }
    }
}
