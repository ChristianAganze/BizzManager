package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.ui.theme.AntigravityBlue
import com.example.mosalisapp.ui.theme.AntigravityCyan
import com.example.mosalisapp.ui.theme.CoralError
import com.example.mosalisapp.ui.theme.DeepIndigo
import com.example.mosalisapp.ui.theme.GlassWhite
import com.example.mosalisapp.ui.theme.GlowCyan
import com.example.mosalisapp.ui.theme.IndigoMidnight
import com.example.mosalisapp.ui.theme.SlateGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerDashboardScreen(
    onNavigateToSales: () -> Unit,
    onNavigateToSaleH: () -> Unit,
    onNavigateToExpenses: () -> Unit,
    onNavigateToDebts: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAgenda: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mosalis Assistant", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IndigoMidnight,
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DeepIndigo,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Accueil") },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AntigravityCyan,
                        selectedTextColor = AntigravityCyan,
                        unselectedIconColor = SlateGrey,
                        unselectedTextColor = SlateGrey,
                        indicatorColor = GlowCyan
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                    label = { Text("Ventes") },
                    selected = selectedItem == 1,
                    onClick = { 
                        selectedItem = 1
                        onNavigateToSales() 
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AntigravityCyan,
                        selectedTextColor = AntigravityCyan,
                        unselectedIconColor = SlateGrey,
                        unselectedTextColor = SlateGrey,
                        indicatorColor = GlowCyan
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Event, contentDescription = null) },
                    label = { Text("Agenda") },
                    selected = selectedItem == 2,
                    onClick = { 
                        selectedItem = 2
                        onNavigateToAgenda() 
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AntigravityCyan,
                        selectedTextColor = AntigravityCyan,
                        unselectedIconColor = SlateGrey,
                        unselectedTextColor = SlateGrey,
                        indicatorColor = GlowCyan
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profil") },
                    selected = selectedItem == 3,
                    onClick = { 
                        selectedItem = 3
                        onNavigateToProfile()
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AntigravityCyan,
                        selectedTextColor = AntigravityCyan,
                        unselectedIconColor = SlateGrey,
                        unselectedTextColor = SlateGrey,
                        indicatorColor = GlowCyan
                    )
                )
            }
        },
        containerColor = IndigoMidnight
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    "Bonjour 👋", 
                    fontSize = 24.sp, 
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            item {
                Text(
                    "Opérations rapides", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.SemiBold,
                    color = SlateGrey
                )
            }
            
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ActionCard("Nouvelle Vente", Icons.Default.Add, AntigravityCyan, Modifier.weight(1f)) {
                        onNavigateToSales()
                    }
                    ActionCard("Dépenses", Icons.Default.ShoppingCart, AntigravityBlue, Modifier.weight(1f)) {
                        onNavigateToExpenses()
                    }
                }
            }

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ActionCard("Gestion des Dettes", Icons.Default.MonetizationOn, CoralError, Modifier.fillMaxWidth()) {
                        onNavigateToDebts()
                    }

                }
            }
            item {
                ActionCard("Historique de vente", Icons.Default.ShoppingCart, AntigravityBlue, Modifier.fillMaxWidth()) {
                    onNavigateToSaleH()
                }
            }


            
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            item {
                Text(
                    "Prochains événements", 
                    fontSize = 18.sp, 
                    fontWeight = FontWeight.SemiBold,
                    color = SlateGrey
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = DeepIndigo),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GlassWhite)
                ) {
                    Box(Modifier.padding(40.dp), contentAlignment = Alignment.Center) {
                        Text(
                            "Rien de prévu pour aujourd'hui", 
                            color = SlateGrey,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(140.dp),
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = DeepIndigo),
        border = androidx.compose.foundation.BorderStroke(1.dp, GlassWhite)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = accentColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    icon, 
                    contentDescription = null, 
                    modifier = Modifier.padding(12.dp).size(28.dp),
                    tint = accentColor
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                title, 
                fontWeight = FontWeight.Bold, 
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}
