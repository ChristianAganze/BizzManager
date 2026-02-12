package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.CartItem
import com.example.mosalisapp.domain.model.Product
import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.repository.SaleRepository
import com.example.mosalisapp.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SaleViewModel(
    private val saleRepository: SaleRepository,
    private val productViewModel: ProductViewModel,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    // Liste de l'historique des ventes
    private val _sales = MutableStateFlow<List<Sale>>(emptyList())
    val sales = _sales.asStateFlow()

    // --- LOGIQUE DU PANIER (CART) ---

    // Articles actuellement dans le panier avant validation
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    // Calcul automatique du montant total du panier
    val totalAmount = _cartItems.map { items ->
        items.sumOf { it.subTotal }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        loadSales()
    }

    fun loadSales() {
        val businessId = authViewModel.currentUser.value?.businessId ?: return
        viewModelScope.launch {
            _sales.value = saleRepository.getSalesByBusiness(businessId)
        }
    }

    // Ajouter un produit au panier ou augmenter sa quantité s'il y est déjà
    fun addToCart(product: Product, qty: Int) {
        if (qty <= 0) return

        val currentItems = _cartItems.value.toMutableList()
        val existingIndex = currentItems.indexOfFirst { it.productId == product.id }

        if (existingIndex != -1) {
            // Le produit est déjà là, on met à jour la quantité et le sous-total
            val item = currentItems[existingIndex]
            val newQty = item.quantity + qty
            currentItems[existingIndex] = item.copy(
                quantity = newQty,
                subTotal = newQty * item.unitPrice
            )
        } else {
            // Nouveau produit dans le panier
            currentItems.add(
                CartItem(
                    productId = product.id,
                    productName = product.name,
                    quantity = qty,
                    unitPrice = product.price,
                    subTotal = qty * product.price
                )
            )
        }
        _cartItems.value = currentItems
    }

    // Supprimer un article du panier
    fun removeFromCart(productId: String) {
        _cartItems.value = _cartItems.value.filter { it.productId != productId }
    }

    // Vider le panier
    fun clearCart() {
        _cartItems.value = emptyList()
    }

    // Enregistrer la vente finale dans Firebase
    fun finalizeSale(customerName: String) {
        val user = authViewModel.currentUser.value ?: return
        if (_cartItems.value.isEmpty()) return

        val newSale = Sale(
            customerName = customerName.ifBlank { "Client Anonyme" },
            items = _cartItems.value,
            totalAmount = _cartItems.value.sumOf { it.subTotal },
            currency = _cartItems.value.firstOrNull()?.unitPrice?.let { "USD" } ?: "USD", // On peut ajuster selon la devise du premier article
            sellerId = user.userId,
            businessId = user.businessId
        )

        viewModelScope.launch {
            val result = saleRepository.addSale(newSale)
            if (result.isSuccess) {
                clearCart()       // Vider le panier après succès
                loadSales()       // Recharger l'historique
                productViewModel.loadProducts() // Rafraîchir les stocks (car déduits)
            }
        }
    }
}