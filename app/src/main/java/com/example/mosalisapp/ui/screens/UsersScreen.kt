package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class User(val id: String, val name: String, val role: String, val permissions: List<String>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onNavigateBack: () -> Unit
) {
    val users = remember { mutableStateListOf(
        User("1", "Alice Dupont", "Worker", listOf("products", "sales")),
        User("2", "Bob Martin", "Worker", listOf("sales", "expenses")),
        User("3", "Owner Admin", "Owner", listOf("all"))
    ) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestion des Utilisateurs") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Implement add user logic */ }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter un utilisateur")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "(OWNER uniquement) Gérer les travailleurs et leurs permissions.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(users) {
                    UserListItem(user = it) {
                        /* TODO: Implement edit user logic */
                    }
                }
            }
        }
    }
}

@Composable
fun UserListItem(user: User, onEditClick: (User) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(user.name, style = MaterialTheme.typography.titleMedium)
                Text(user.role, style = MaterialTheme.typography.bodySmall)
                Text("Permissions: ${user.permissions.joinToString()}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onEditClick(user) }) {
                Icon(Icons.Default.Edit, contentDescription = "Modifier utilisateur")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUsersScreen() {
    UsersScreen(onNavigateBack = {})
}
