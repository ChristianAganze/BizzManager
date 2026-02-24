package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.ui.viewmodel.WorkerManagementViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddWorker: () -> Unit = {},
    viewModel: WorkerManagementViewModel = koinViewModel()
) {
    val workers by viewModel.workers.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mes Employés") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddWorker) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter un employé")
            }
        }
    ) { padding ->
        if (workers.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Aucun employé trouvé", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workers) { worker ->
                    WorkerItem(worker)
                }
            }
        }
    }
}

@Composable
fun WorkerItem(worker: User) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Group,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(worker.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(worker.email, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
