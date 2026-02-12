package com.example.mosalisapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mosalisapp.domain.model.Expense
import com.example.mosalisapp.domain.repository.ExpenseRepository
import com.example.mosalisapp.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val repository: ExpenseRepository,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses = _expenses.asStateFlow()

    // Calcul du total des dépenses pour l'affichage OWNER
    val totalExpenses = _expenses.map { list ->
        list.sumOf { it.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init { loadExpenses() }

    fun loadExpenses() {
        val businessId = authViewModel.currentUser.value?.businessId ?: return
        viewModelScope.launch {
            _expenses.value = repository.getExpenses(businessId)
        }
    }

    fun addExpense(title: String, amount: Double, category: String, desc: String, currency: String) {
        val user = authViewModel.currentUser.value ?: return
        val expense = Expense(
            title = title,
            amount = amount,
            category = category,
            description = desc,
            currency = currency,
            businessId = user.businessId
        )
        viewModelScope.launch {
            repository.addExpense(expense).onSuccess { loadExpenses() }
        }
    }
}