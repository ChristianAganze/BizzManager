package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.CartItem
import com.example.mosalisapp.domain.model.Debt
import com.example.mosalisapp.domain.model.Expense
import com.example.mosalisapp.domain.model.Product
import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.usecase.CreateClientUseCase
import com.example.mosalisapp.domain.usecase.CreateDebtUseCase
import com.example.mosalisapp.domain.usecase.CreateExpenseUseCase
import com.example.mosalisapp.domain.usecase.CreateSaleUseCase
import com.example.mosalisapp.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WorkerViewModel(
    private val createSaleUseCase: CreateSaleUseCase,
    private val createDebtUseCase: CreateDebtUseCase,
    private val createExpenseUseCase: CreateExpenseUseCase,
    private val createClientUseCase: CreateClientUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<WorkerUiState>(WorkerUiState.Idle)
    val uiState: StateFlow<WorkerUiState> = _uiState

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart

    val products: StateFlow<List<Product>> = authViewModel.currentUser
        .flatMapLatest { user ->
            if (user == null) flowOf(emptyList())
            else getProductsUseCase(user.businessId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Tous")
    val selectedCategory = _selectedCategory.asStateFlow()

    val categories: StateFlow<List<String>> = products.map { list ->
        listOf("Tous") + list.map { it.category }.distinct().filter { it.isNotEmpty() }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf("Tous"))

    val filteredProducts = combine(products, _searchQuery, _selectedCategory) { products, query, category ->
        products.filter { product ->
            val matchesQuery = product.name.contains(query, ignoreCase = true)
            val matchesCategory = category == "Tous" || product.category == category
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategoryChange(category: String) {
        _selectedCategory.value = category
    }

    fun addToCart(product: Product) {
        updateItemQuantity(product, 1)
    }

    fun removeFromCart(product: Product) {
        updateItemQuantity(product, -1)
    }

    fun updateItemQuantity(product: Product, delta: Int) {
        val currentCart = _cart.value.toMutableList()
        val existingItemIndex = currentCart.indexOfFirst { it.product.id == product.id }
        
        if (existingItemIndex != -1) {
            val existingItem = currentCart[existingItemIndex]
            val newQuantity = existingItem.quantity + delta
            if (newQuantity > 0) {
                currentCart[existingItemIndex] = existingItem.copy(quantity = newQuantity)
            } else {
                currentCart.removeAt(existingItemIndex)
            }
        } else if (delta > 0) {
            currentCart.add(CartItem(product, delta))
        }
        _cart.value = currentCart
    }

    fun clearCart() {
        _cart.value = emptyList()
    }

    fun validateCart() {
        if (_cart.value.isEmpty()) return

        viewModelScope.launch {
            _uiState.value = WorkerUiState.Loading
            val currentUser = authViewModel.currentUser.value ?: return@launch
            
            var successCount = 0
            val items = _cart.value

            items.forEach { item ->
                val sale = Sale(
                    businessId = currentUser.businessId,
                    productId = item.product.id,
                    quantity = item.quantity,
                    totalAmount = item.totalAmount,
                    createdBy = currentUser.id
                )
                val result = createSaleUseCase(sale)
                if (result.isSuccess) {
                    successCount++
                }
            }

            if (successCount == items.size) {
                _cart.value = emptyList()
                _uiState.value = WorkerUiState.Success("Vente validée avec succès")
            } else if (successCount > 0) {
                _uiState.value = WorkerUiState.Error("Certains produits n'ont pas pu être vendus (vérifiez vos stocks)")
            } else {
                _uiState.value = WorkerUiState.Error("Échec de la validation de la vente")
            }
        }
    }

    fun createDebt(clientId: String, amount: Double) {
        viewModelScope.launch {
            _uiState.value = WorkerUiState.Loading
            val currentUser = authViewModel.currentUser.value ?: return@launch
            val debt = Debt(
                businessId = currentUser.businessId,
                clientId = clientId,
                amount = amount
            )
            val result = createDebtUseCase(debt)
            if (result.isSuccess) {
                _uiState.value = WorkerUiState.Success("Dette créée avec succès")
            }
        }
    }

    fun createExpense(title: String, amount: Double) {
        viewModelScope.launch {
            _uiState.value = WorkerUiState.Loading
            val currentUser = authViewModel.currentUser.value ?: return@launch
            val expense = Expense(
                businessId = currentUser.businessId,
                title = title,
                amount = amount,
                createdBy = currentUser.id
            )
            val result = createExpenseUseCase(expense)
            if (result.isSuccess) {
                _uiState.value = WorkerUiState.Success("Dépense créée avec succès")
            } else {
                _uiState.value = WorkerUiState.Error(result.exceptionOrNull()?.message ?: "Erreur lors de la création")
            }
        }
    }
}

sealed class WorkerUiState {
    object Idle : WorkerUiState()
    object Loading : WorkerUiState()
    data class Success(val message: String) : WorkerUiState()
    data class Error(val message: String) : WorkerUiState()
}
