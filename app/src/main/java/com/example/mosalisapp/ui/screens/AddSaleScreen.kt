package com.example.mosalisapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.ui.viewmodel.ProductViewModel
import com.example.mosalisapp.ui.viewmodel.SaleViewModel
import org.koin.compose.viewmodel.koinViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSaleScreen(
    onNavigateBack: () -> Unit,
    saleViewModel: SaleViewModel = koinViewModel(),
    productViewModel: ProductViewModel = koinViewModel()
) {
    val cartItems by saleViewModel.cartItems.collectAsState()
    val products by productViewModel.filteredProducts.collectAsState()
    val searchQuery by productViewModel.searchQuery.collectAsState()

    // État pour afficher ou masquer la vue du panier
    var isCartSheetOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nouvelle Vente") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    // Icône Panier avec badge s'il y a des articles
                    BadgedBox(
                        badge = {
                            if (cartItems.isNotEmpty()) {
                                Badge { Text(cartItems.size.toString()) }
                            }
                        },
                        modifier = Modifier.padding(end = 16.dp).clickable { isCartSheetOpen = true }
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Voir le panier")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // 1. BARRE DE RECHERCHE SEULEMENT
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { productViewModel.onSearchQueryChange(it) },
                label = { Text("Rechercher un produit...") },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true
            )

            // 2. LISTE DE PRODUITS
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(products) { product ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                        onClick = { saleViewModel.addToCart(product, 1) }
                    ) {
                        ListItem(
                            headlineContent = { Text(product.name, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text("${product.price} ${product.currency} | Stock: ${product.quantity}") },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Ajouter", // Correction ici
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }
        }

        // 3. LA VUE DU PANIER (Modal Bottom Sheet ou Dialog)
        if (isCartSheetOpen) {
            CartDialog(
                onDismiss = { isCartSheetOpen = false },
                saleViewModel = saleViewModel,
                onSaleSuccess = {
                    isCartSheetOpen = false
                    onNavigateBack()
                }
            )
        }
    }
}
@Composable
fun CartDialog(
    onDismiss: () -> Unit,
    saleViewModel: SaleViewModel,
    onSaleSuccess: () -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    val cartItems by saleViewModel.cartItems.collectAsState()
    val totalAmount by saleViewModel.totalAmount.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Résumé de la vente") },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Nom du Client") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Articles sélectionnés :", fontWeight = FontWeight.Bold)

                LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                    items(cartItems) { item ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${item.productName} (x${item.quantity})", modifier = Modifier.weight(1f))
                            Text("${item.subTotal} ${item.unitPrice}") // On affiche le sous-total
                        }
                    }
                }

                HorizontalDivider()

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("TOTAL GLOBAL", fontWeight = FontWeight.ExtraBold)
                    Text("$totalAmount USD", fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    saleViewModel.finalizeSale(customerName)
                    onSaleSuccess()
                },
                enabled = customerName.isNotBlank() && cartItems.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ENREGISTRER LA VENTE")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Continuer les achats") }
        }
    )
}