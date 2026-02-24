package com.example.mosalisapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.ui.viewmodel.SaleViewModel
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun SalesScreen(
    onNavigateToAddSale: () -> Unit, // Navigation vers l'écran de caisse qu'on a créé
    onNavigateBack: () -> Unit,
    saleViewModel: SaleViewModel = koinViewModel()
) {
    val sales by saleViewModel.sales.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historique des Ventes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToAddSale,
                icon = { Icon(Icons.Default.Add, null) },
                text = { Text("Nouvelle Vente") }
            )
        }
    ) { padding ->
        if (sales.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Aucune vente enregistrée", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sales) { sale ->
                    SaleHistoryCard(sale = sale)
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun SaleHistoryCard(sale: Sale) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = sale.customerName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = SimpleDateFormat("dd/MM HH:mm").format(Date(sale.timestamp)),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Spacer(Modifier.height(8.dp))

            // Liste résumée des items
            sale.items.forEach { item ->
                Text(
                    text = "• ${item.productName} (x${item.quantity})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total", fontWeight = FontWeight.SemiBold)
                Text(
                    "${sale.totalAmount} ${sale.currency}",
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
            }
        }
    }
}