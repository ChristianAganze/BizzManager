package com.example.mosalisapp.ui.screens.company

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.viewmodel.CreateCompanyViewModel
import com.example.mosalisapp.ui.viewmodel.CreationState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateCompanyScreen(
    onSuccess: () -> Unit,
    viewModel: CreateCompanyViewModel = koinViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phoneCompany by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val creationState by viewModel.creationState.collectAsState()

    LaunchedEffect(creationState) {
        if (creationState is CreationState.Success) {
            onSuccess()
            viewModel.resetCreationState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Créer votre entreprise", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom de l'entreprise") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneCompany,
            onValueChange = { phoneCompany = it },
            label = { Text("Téléphone de l'entreprise") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Catégorie (ex: restaurant, magasin)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Adresse de l'entreprise") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.createCompany(name, phoneCompany, category, address)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = creationState !is CreationState.Loading
        ) {
            if (creationState is CreationState.Loading) {
                CircularProgressIndicator()
            } else {
                Text("Créer l'entreprise")
            }
        }

        if (creationState is CreationState.Error) {
            Text(
                text = (creationState as CreationState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
