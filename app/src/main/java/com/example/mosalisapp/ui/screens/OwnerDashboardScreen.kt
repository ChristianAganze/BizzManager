package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.domain.model.DashboardStats
import com.example.mosalisapp.ui.viewmodel.DashboardState
import com.example.mosalisapp.ui.viewmodel.DashboardViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerDashboardScreen(
    onNavigateToWorkerList: () -> Unit,
    onNavigateToClientList: () -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.dashboardState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Propriétaire") },
                actions = {
                    IconButton(onClick = onNavigateToWorkerList) {
                        Icon(Icons.Default.Group, contentDescription = "Employés")
                    }
                    IconButton(onClick = onNavigateToClientList) {
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
            when (val current = state) {
                is DashboardState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DashboardState.Success -> {
                    DashboardContent(current.stats)
                }
                is DashboardState.Error -> {
                    Text(
                        text = current.message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
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
        item { StatCards(stats) }
        item { SalesRevenueChart(stats) }
        item { ExpenseDebtChart(stats) }

        if (stats.lowStockCount > 0) {
            item { LowStockWarning(stats.lowStockCount) }
        }
    }
}

@Composable
fun SalesRevenueChart(stats: DashboardStats) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(stats) {
        modelProducer.runTransaction {
            lineSeries {
                // Utilisation explicite des points x et y pour la clarté
                series(
                    x = listOf(0, 1, 2),
                    y = listOf(
                        stats.dailySalesAmount.toFloat(),
                        stats.monthlySalesAmount.toFloat(),
                        stats.totalRevenue.toFloat()
                    )
                )
            }
        }
    }

    ChartSection("Ventes et Revenus") {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            ),
            modelProducer = modelProducer
        )
    }
}

@Composable
fun ExpenseDebtChart(stats: DashboardStats) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(stats) {
        modelProducer.runTransaction {
            columnSeries {
                series(
                    x = listOf(0, 1),
                    y = listOf(
                        stats.totalExpenses.toFloat(),
                        stats.totalDebtAmount.toFloat()
                    )
                )
            }
        }
    }

    ChartSection("Dépenses vs Dettes") {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberColumnCartesianLayer(),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            ),
            modelProducer = modelProducer
        )
    }
}

@Composable
fun StatCards(stats: DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            label = "Revenu Total",
            value = "${stats.totalRevenue}$",
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Dettes",
            value = "${stats.totalDebtAmount}$",
            containerColor = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 12.sp)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ChartSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Box(modifier = Modifier.height(220.dp)) {
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
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$count produits presque en rupture de stock !",
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}