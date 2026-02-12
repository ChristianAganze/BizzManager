package com.app.businessmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.businessmanager.domain.model.DashboardStats
import com.app.businessmanager.ui.viewmodel.DashboardState
import com.app.businessmanager.ui.viewmodel.DashboardViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerDashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.dashboardState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Propriétaire") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.WorkerList) }) {
                        Icon(Icons.Default.Group, contentDescription = "Employés")
                    }
                    IconButton(onClick = { navController.navigate(Screen.ClientList) }) {
                        Icon(Icons.Default.Person, contentDescription = "Clients")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val currentState = state) {
                is DashboardState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                is DashboardState.Success -> DashboardContent(currentState.stats)
                is DashboardState.Error -> Text(currentState.message, Modifier.align(Alignment.Center), color = Color.Red)
            }
        }
    }
}

@Composable
fun DashboardContent(stats: DashboardStats) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StatCards(stats)
        }
        
        item {
            ChartSection("Ventes et Revenus") {
                val chartEntryModel = entryModelOf(stats.dailySalesAmount, stats.monthlySalesAmount, stats.totalRevenue)
                Chart(
                    chart = lineChart(),
                    model = chartEntryModel,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                )
            }
        }

        item {
            ChartSection("Dépenses vs Dettes") {
                val chartEntryModel = entryModelOf(stats.totalExpenses, stats.totalDebtAmount)
                Chart(
                    chart = columnChart(),
                    model = chartEntryModel,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                )
            }
        }
        
        if (stats.lowStockCount > 0) {
            item {
                LowStockWarning(stats.lowStockCount)
            }
        }
    }
}

@Composable
fun StatCards(stats: DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard("Revenu Total", "${stats.totalRevenue}$", Modifier.weight(1f), MaterialTheme.colorScheme.primaryContainer)
        StatCard("Dettes", "${stats.totalDebtAmount}$", Modifier.weight(1f), MaterialTheme.colorScheme.errorContainer)
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier, containerColor: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(label, fontSize = 12.sp)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ChartSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Box(Modifier.height(200.dp)) {
                content()
            }
        }
    }
}

@Composable
fun LowStockWarning(count: Int) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            Spacer(Modifier.width(8.dp))
            Text("$count produits sont presque en rupture de stock !", color = MaterialTheme.colorScheme.onErrorContainer)
        }
    }
}
