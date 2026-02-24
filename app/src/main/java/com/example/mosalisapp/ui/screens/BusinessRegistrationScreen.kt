package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.viewmodel.BusinessRegistrationState
import com.example.mosalisapp.ui.viewmodel.BusinessViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessRegistrationScreen(
    onRegistrationSuccess: () -> Unit,
    viewModel: BusinessViewModel = koinViewModel()
) {
    var businessName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val currencies = listOf("CDF", "USD")
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }
    var phoneBusiness by remember { mutableStateOf("") }

    val registrationState by viewModel.registrationState.collectAsState()

    LaunchedEffect(registrationState) {
        if (registrationState is BusinessRegistrationState.Success) {
            onRegistrationSuccess()
            viewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Détails de votre Business", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = businessName,
            onValueChange = { businessName = it },
            label = { Text("Nom du Business") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Catégorie (ex: Boutique, Restaurant)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Adresse Physique") },
            modifier = Modifier.fillMaxWidth()
        )

        Column(Modifier.selectableGroup()) {
            Text("Devise de gestion :")
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                currencies.forEach { text ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (text == selectedCurrency),
                                onClick = { selectedCurrency = text },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedCurrency),
                            onClick = null
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = phoneBusiness,
            onValueChange = { phoneBusiness = it },
            label = { Text("Téléphone du Business") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (registrationState is BusinessRegistrationState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    viewModel.registerBusiness(
                        name = businessName,
                        category = category,
                        address = address,
                        currency = selectedCurrency,
                        phone = phoneBusiness
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = businessName.isNotBlank()
            ) {
                Text("Finaliser la création")
            }
        }

        if (registrationState is BusinessRegistrationState.Error) {
            Text(
                text = (registrationState as BusinessRegistrationState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBusinessRegistrationScreen() {
    BusinessRegistrationScreen(onRegistrationSuccess = {})
}