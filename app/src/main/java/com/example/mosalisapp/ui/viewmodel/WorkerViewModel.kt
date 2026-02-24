package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.Debt
import com.example.mosalisapp.domain.model.Expense
import com.example.mosalisapp.domain.model.Sale
import com.example.mosalisapp.domain.usecase.CreateClientUseCase
import com.example.mosalisapp.domain.usecase.CreateDebtUseCase
import com.example.mosalisapp.domain.usecase.CreateSaleUseCase
import com.example.mosalisapp.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkerViewModel(
    private val createSaleUseCase: CreateSaleUseCase,
    private val createDebtUseCase: CreateDebtUseCase,
    private val createClientUseCase: CreateClientUseCase,
    private val getProductsUseCase: GetProductsUseCase,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<WorkerUiState>(WorkerUiState.Idle)
    val uiState: StateFlow<WorkerUiState> = _uiState

    fun createSale(productId: String, quantity: Int, amount: Double) {
        viewModelScope.launch {
            _uiState.value = WorkerUiState.Loading
            val currentUser = authViewModel.currentUser.value ?: return@launch
            val sale = Sale(
                businessId = currentUser.businessId,
                productId = productId,
                quantity = quantity,
                totalAmount = amount,
                createdBy = currentUser.id
            )
            val result = createSaleUseCase(sale)
            if (result.isSuccess) {
                _uiState.value = WorkerUiState.Success("Vente créée avec succès")
            } else {
                _uiState.value = WorkerUiState.Error(result.exceptionOrNull()?.message ?: "Erreur inconnue")
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
            // Simulating success for now
            _uiState.value = WorkerUiState.Success("Dépense créée avec succès")
        }
    }
}

sealed class WorkerUiState {
    object Idle : WorkerUiState()
    object Loading : WorkerUiState()
    data class Success(val message: String) : WorkerUiState()
    data class Error(val message: String) : WorkerUiState()
}
