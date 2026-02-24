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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessRegistrationScreen(
    onRegistrationSuccess: () -> Unit
) {
    var businessName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val currencies = listOf("CDF", "USD")
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }
    var phoneBusiness by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneOwner by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Créer votre Business et compte Owner", style = MaterialTheme.typography.headlineSmall)

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
            label = { Text("Catégorie") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Adresse") },
            modifier = Modifier.fillMaxWidth()
        )

        Column(Modifier.selectableGroup()) {
            Text("Devise :")
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
                            onClick = null // null recommended for accessibility with screenreaders
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
            label = { Text("Téléphone Business") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Nom du Propriétaire") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email du Propriétaire") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phoneOwner,
            onValueChange = { phoneOwner = it },
            label = { Text("Téléphone du Propriétaire") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                 onRegistrationSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Créer le Business et le Compte")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBusinessRegistrationScreen() {
    BusinessRegistrationScreen(onRegistrationSuccess = {})
}