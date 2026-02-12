package com.app.businessmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.businessmanager.ui.components.BusinessButton
import com.app.businessmanager.ui.components.BusinessTextField
import com.app.businessmanager.ui.viewmodel.ClientViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClientScreen(
    navController: NavController,
    viewModel: ClientViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ajouter un Client") })
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
            BusinessTextField(value = phone, onValueChange = { phone = it }, label = "Téléphone")
            BusinessTextField(value = email, onValueChange = { email = it }, label = "Email (optionnel)")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            BusinessButton(
                text = "Enregistrer le client",
                onClick = {
                    viewModel.addClient(name, phone, email)
                    navController.popBackStack()
                }
            )
        }
    }
}
