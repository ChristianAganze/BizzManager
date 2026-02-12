package com.example.mosalisapp.ui.screens

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.screens.auth.AuthViewModel
import com.example.mosalisapp.ui.viewmodel.DebtViewModel
import org.koin.compose.viewmodel.koinViewModel

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsScreen(
    onNavigateBack: () -> Unit,
    viewModel: DebtViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    val debts by viewModel.debts.collectAsState()
    val totalPending by viewModel.totalPendingDebt.collectAsState()
    val user by authViewModel.currentUser.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    // Vérification des permissions
    val canAccess = user?.role == "OWNER" || user?.permissions?.get("debts") == true

    if (!canAccess) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Accès refusé. Contactez l'administrateur.")
        }
        return
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestion des Dettes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, "Ajouter Dette")
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            // Résumé des dettes
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Total Dettes en attente", style = MaterialTheme.typography.labelLarge)
                    Text("$totalPending USD", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                }
            }

            LazyColumn {
                items(debts) { debt ->
                    ListItem(
                        headlineContent = { Text(debt.customerName, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("${debt.description} • ${SimpleDateFormat("dd/MM").format(debt.timestamp)}") },
                        trailingContent = {
                            Column(horizontalAlignment = Alignment.End) {
                                Text("${debt.amount} ${debt.currency}", color = if (debt.isPaid) Color.Gray else Color.Red)
                                Switch(checked = debt.isPaid, onCheckedChange = { viewModel.toggleDebtStatus(debt) })
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }

        if (showDialog) {
            AddDebtDialog(
                onDismiss = { showDialog = false },
                onConfirm = { name, amt, curr, desc ->
                    viewModel.addDebt(name, amt, curr, desc)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddDebtDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Double, String, String) -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val currencies = listOf("USD", "CDF")
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouvelle Dette Client") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Nom du client
                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Nom du Client") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Montant + Devise
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Montant dû") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )

                    Spacer(Modifier.width(8.dp))

                    // Sélecteur rapide USD/CDF
                    Column {
                        currencies.forEach { curr ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = (selectedCurrency == curr),
                                    onClick = { selectedCurrency = curr }
                                )
                                Text(curr, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                // Description / Motif
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Motif (ex: 2 sacs de riz)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        customerName,
                        amount.toDoubleOrNull() ?: 0.0,
                        selectedCurrency,
                        description
                    )
                },
                enabled = customerName.isNotBlank() && amount.isNotBlank()
            ) {
                Text("Enregistrer la dette")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}