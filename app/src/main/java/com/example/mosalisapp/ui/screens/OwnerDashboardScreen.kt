package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerDashboardScreen(
    onNavigateToUsers: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToSales: () -> Unit,
    onNavigateToExpenses: () -> Unit,
    onNavigateToDebts: () -> Unit,
    onNavigateToAgenda: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Mosalis App",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Employés") },
                    selected = false,
                    icon = { Icon(Icons.Default.People, contentDescription = null) },
                    onClick = { scope.launch { drawerState.close() }; onNavigateToUsers() }
                )
                NavigationDrawerItem(
                    label = { Text("Produits") },
                    selected = false,
                    icon = { Icon(Icons.Default.Inventory, contentDescription = null) },
                    onClick = { scope.launch { drawerState.close() }; onNavigateToProducts() }
                )
                NavigationDrawerItem(
                    label = {Text("Dettes")},
                    selected = false,
                    icon = {Icon(Icons.AutoMirrored.Default.ReceiptLong, contentDescription = "")},
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToDebts()
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Agenda") },
                    selected = false,
                    icon = { Icon(Icons.Default.Event, contentDescription = null) },
                    onClick = { scope.launch { drawerState.close() }; onNavigateToAgenda() }
                )
                // Tu peux ajouter d'autres items ici

                Spacer(Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Déconnexion") },
                    selected = false,
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
                    onClick = { scope.launch { drawerState.close() }; onLogout() }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Tableau de Bord") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToProfile) {
                            Icon(Icons.Filled.Person, contentDescription = "Profil")
                        }
                        IconButton(onClick = onLogout) {
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Déconnexion")
                        }

                    }
                )
            }
        ) { paddingValues ->
            // Voici la fonction qui manquait
            DashboardContent(
                padding = paddingValues,
                onNavigateToUsers = onNavigateToUsers,
                onNavigateToProducts = onNavigateToProducts,
                onNavigateToSales = onNavigateToSales,
                onNavigateToExpenses = onNavigateToExpenses,
                onNavigateToDebts = onNavigateToDebts,
                onNavigateToAgenda = onNavigateToAgenda
            )
        }
    }
}

@Composable
fun DashboardContent(
    padding: PaddingValues,
    onNavigateToUsers: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToSales: () -> Unit,
    onNavigateToExpenses: () -> Unit,
    onNavigateToDebts: () -> Unit,
    onNavigateToAgenda: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { DashboardCard("Ventes", Icons.Default.ShoppingCart, Color(0xFF4CAF50), onNavigateToSales) }
        item { DashboardCard("Produits", Icons.Default.Inventory, Color(0xFF2196F3), onNavigateToProducts) }
        item { DashboardCard("Dépenses", Icons.Default.MoneyOff, Color(0xFFF44336), onNavigateToExpenses) }
        item { DashboardCard("Employés", Icons.Default.People, Color(0xFFFF9800), onNavigateToUsers) }
        item { DashboardCard("Dettes",
            Icons.AutoMirrored.Filled.ReceiptLong, Color(0xFF9C27B0), onNavigateToDebts) }
        item { DashboardCard("Agenda", Icons.Default.Event, Color(0xFF607D8B), onNavigateToAgenda) }
    }
}

@Composable
fun DashboardCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.height(120.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = color)
        }
    }
}