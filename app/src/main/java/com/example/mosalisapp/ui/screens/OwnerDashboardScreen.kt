package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.mosalisapp.ui.theme.*
import kotlinx.coroutines.launch
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
    onNavigateToProducts: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onLogout: () -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.dashboardState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = DeepIndigo,
                drawerContentColor = Color.White
            ) {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Mosalis Pro",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = AntigravityCyan,
                    fontWeight = FontWeight.Bold
                )
                Divider(color = GlassWhite, modifier = Modifier.padding(horizontal = 16.dp))
                NavigationDrawerItem(
                    label = { Text("Tableau de bord") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = GlowCyan,
                        selectedTextColor = AntigravityCyan,
                        selectedIconColor = AntigravityCyan,
                        unselectedTextColor = SlateGrey,
                        unselectedIconColor = SlateGrey
                    )
                )
                NavigationDrawerItem(
                    label = { Text("Employés") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        onNavigateToWorkerList() 
                    },
                    icon = { Icon(Icons.Default.Group, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White,
                        unselectedIconColor = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { Text("Clients") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        onNavigateToClientList() 
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White,
                        unselectedIconColor = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { Text("Produits") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        onNavigateToProducts() 
                    },
                    icon = { Icon(Icons.Default.Inventory, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White,
                        unselectedIconColor = Color.White
                    )
                )
                Spacer(Modifier.weight(1f))
                NavigationDrawerItem(
                    label = { Text("Profil") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        onNavigateToProfile() 
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White,
                        unselectedIconColor = Color.White
                    )
                )
                NavigationDrawerItem(
                    label = { Text("Déconnexion") },
                    selected = false,
                    onClick = { 
                        scope.launch { drawerState.close() }
                        onLogout() 
                    },
                    icon = { Icon(Icons.Default.Logout, contentDescription = null) },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = Color.White,
                        unselectedIconColor = Color.White
                    )
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },
            containerColor = IndigoMidnight
        ) { padding ->
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when (val current = state) {
                    is DashboardState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = AntigravityCyan
                        )
                    }
                    is DashboardState.Success -> {
                        DashboardContent(current.stats)
                    }
                    is DashboardState.Error -> {
                        Text(
                            text = current.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
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
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { StatCards(stats) }
        item { SalesRevenueChart(stats) }
        item { ExpenseDebtChart(stats) }

        if (stats.lowStockCount > 0) {
            item { LowStockWarning(stats.lowStockCount) }
        }
        
        item { Spacer(modifier = Modifier.height(20.dp)) }
    }
}

@Composable
fun SalesRevenueChart(stats: DashboardStats) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(stats) {
        modelProducer.runTransaction {
            lineSeries {
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

    ChartSection("Ventes & Revenus") {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            ),
            modelProducer = modelProducer,
            modifier = Modifier.fillMaxSize()
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
            modelProducer = modelProducer,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun StatCards(stats: DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            label = "Revenu Total",
            value = "${stats.totalRevenue}$",
            containerColor = DeepIndigo,
            accentColor = AntigravityCyan,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Dettes",
            value = "${stats.totalDebtAmount}$",
            containerColor = DeepIndigo,
            accentColor = CoralError,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    containerColor: Color,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, GlassWhite)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = label, fontSize = 12.sp, color = SlateGrey)
            Text(
                text = value, 
                fontSize = 22.sp, 
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }
    }
}

@Composable
fun ChartSection(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DeepIndigo),
        border = androidx.compose.foundation.BorderStroke(1.dp, GlassWhite)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Box(modifier = Modifier.height(240.dp)) {
                content()
            }
        }
    }
}

@Composable
fun LowStockWarning(count: Int) {
    Surface(
        color = Color(0x33FF4C3C),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        border = androidx.compose.foundation.BorderStroke(1.dp, CoralError.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = CoralError
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "$count produits presque en rupture !",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}