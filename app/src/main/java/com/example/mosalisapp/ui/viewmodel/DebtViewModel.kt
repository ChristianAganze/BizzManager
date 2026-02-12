package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.Debt
import com.example.mosalisapp.domain.repository.DebtRepository
import com.example.mosalisapp.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DebtViewModel(
    private val repository: DebtRepository,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _debts = MutableStateFlow<List<Debt>>(emptyList())
    val debts = _debts.asStateFlow()

    // Somme des dettes en attente (non payées)
    val totalPendingDebt = _debts.map { list ->
        list.filter { !it.isPaid }.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init { loadDebts() }

    fun loadDebts() {
        val bId = authViewModel.currentUser.value?.businessId ?: return
        viewModelScope.launch { _debts.value = repository.getDebts(bId) }
    }

    fun addDebt(name: String, amount: Double, currency: String, desc: String) {
        val user = authViewModel.currentUser.value ?: return
        val newDebt = Debt(
            customerName = name, amount = amount, currency = currency,
            description = desc, businessId = user.businessId
        )
        viewModelScope.launch { repository.addDebt(newDebt).onSuccess { loadDebts() } }
    }

    fun toggleDebtStatus(debt: Debt) {
        viewModelScope.launch {
            repository.updateDebtStatus(debt.id, !debt.isPaid).onSuccess { loadDebts() }
        }
    }
}