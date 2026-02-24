package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerDashboardScreen(
    onNavigateToSales: () -> Unit,
    onNavigateToExpenses: () -> Unit,
    onNavigateToDebts: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Tableau de bord Travailleur")
                },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Opérations rapides", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ActionCard("Nouvelle Vente", Icons.Default.Add, Modifier.weight(1f)) {
                    onNavigateToSales()
                }
                ActionCard("Nouvelle Dette", Icons.Default.Money, Modifier.weight(1f)) {
                    onNavigateToDebts()
                }
            }
            
            ActionCard("Nouvelle Dépense", Icons.Default.List, Modifier.fillMaxWidth()) {
                    onNavigateToExpenses()
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Agenda", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            
            Card(Modifier.fillMaxWidth()) {
                Box(Modifier.padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("Aucun événement aujourd'hui", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(120.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
        }
    }
}
