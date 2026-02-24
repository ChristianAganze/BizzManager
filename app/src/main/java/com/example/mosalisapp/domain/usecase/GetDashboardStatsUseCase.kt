package com.example.mosalisapp.domain.usecase

import com.example.mosalisapp.domain.model.DashboardStats
import com.example.mosalisapp.domain.repository.DebtRepository
import com.example.mosalisapp.domain.repository.ExpenseRepository
import com.example.mosalisapp.domain.repository.ProductRepository
import com.example.mosalisapp.domain.repository.SaleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDashboardStatsUseCase(
    private val productRepository: ProductRepository,
    private val saleRepository: SaleRepository,
    private val debtRepository: DebtRepository,
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(businessId: String): Flow<DashboardStats> {
        return combine(
            productRepository.getProducts(businessId),
            saleRepository.getSales(businessId),
            debtRepository.getDebts(businessId),
            expenseRepository.getExpenses(businessId)
        ) { products, sales, debts, expenses ->
            val lowStockCount = products.count { it.quantity < 5 }
            val totalSales = sales.sumOf { it.totalAmount }
            val totalDebts = debts.filter { !it.isPaid }.sumOf { it.amount }
            val totalExpenses = expenses.sumOf { it.amount }
            
            // Simple logic for Demo, real app would filter by day/month
            DashboardStats(
                lowStockCount = lowStockCount,
                dailySalesAmount = totalSales, // Placeholder
                monthlySalesAmount = totalSales, // Placeholder
                totalDebtAmount = totalDebts,
                totalRevenue = totalSales - totalExpenses,
                totalExpenses = totalExpenses
            )
        }
    }
}
