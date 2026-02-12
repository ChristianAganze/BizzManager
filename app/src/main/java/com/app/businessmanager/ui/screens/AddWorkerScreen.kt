package com.app.businessmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.businessmanager.ui.components.BusinessButton
import com.app.businessmanager.ui.components.BusinessTextField
import com.app.businessmanager.ui.viewmodel.WorkerManagementViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkerScreen(
    navController: NavController,
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
                    navController.popBackStack()
                }
            )
        }
    }
}
