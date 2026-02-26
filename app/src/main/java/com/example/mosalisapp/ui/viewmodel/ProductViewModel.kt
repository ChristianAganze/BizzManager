package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.Product
import com.example.mosalisapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Liste filtrée : se met à jour quand _products OU _searchQuery change
    val filteredProducts = combine(_products, _searchQuery) { products, query ->
        if (query.isBlank()) {
            products
        } else {
            products.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init { loadProducts() }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
    fun loadProducts() {
        val businessId = authViewModel.currentUser.value?.businessId ?: return
        viewModelScope.launch {
            _isLoading.value = true
            _products.value = repository.getProducts(businessId).first()
            _isLoading.value = false
        }
    }

    fun addOrUpdateProduct(name: String, category: String, price: Double, quantity: Int, currency: String, id: String? = null) {
        val businessId = authViewModel.currentUser.value?.businessId ?: return
        val product = Product(
            id = id ?: "",
            name = name,
            category = category,
            price = price,
            quantity = quantity,
            currency = currency,
            businessId = businessId
        )

        viewModelScope.launch {
            if (id == null) repository.addProduct(product)
            else repository.updateProduct(product)
            loadProducts()
        }
    }
    fun deleteProduct(id: String) {
        viewModelScope.launch {
            repository.deleteProduct(id)
            loadProducts()
        }
    }
}