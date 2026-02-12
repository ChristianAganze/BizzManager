package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
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
import com.example.mosalisapp.ui.viewmodel.ExpenseViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    onNavigateBack: () -> Unit,
    viewModel: ExpenseViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    val total by viewModel.totalExpenses.collectAsState()
    val user by authViewModel.currentUser.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dépenses") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter dépense")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Affichage du total uniquement pour l'OWNER
            if (user?.role == "OWNER") {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Total des dépenses", style = MaterialTheme.typography.labelLarge)
                        Text("$total USD", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(expenses) { expense ->
                    ListItem(
                        headlineContent = { Text(expense.title) },
                        supportingContent = { Text("${expense.category} • ${expense.description}") },
                        trailingContent = {
                            Text("- ${expense.amount} ${expense.currency}", color = Color.Red, fontWeight = FontWeight.Bold)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }

        if (showDialog) {
            AddExpenseDialog(
                onDismiss = { showDialog = false },
                onConfirm = { title, amount, cat, desc, curr ->
                    viewModel.addExpense(title, amount, cat, desc, curr)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun AddExpenseDialog(onDismiss: () -> Unit, onConfirm: (String, Double, String, String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Général") }
    val currencies = listOf("USD", "CDF")
    var selectedCurrency by remember { mutableStateOf(currencies[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouvelle Dépense") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Titre (ex: Loyer)") }, modifier = Modifier.fillMaxWidth())

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Montant") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    // Sélecteur rapide USD/CDF
                    Column {
                        currencies.forEach { curr ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = (selectedCurrency == curr), onClick = { selectedCurrency = curr })
                                Text(curr)
                            }
                        }
                    }
                }

                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Commentaire") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(title, amount.toDoubleOrNull() ?: 0.0, category, desc, selectedCurrency) }) {
                Text("Enregistrer")
            }
        }
    )
}