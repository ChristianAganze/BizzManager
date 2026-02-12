package com.app.businessmanager.domain.model

data class DashboardStats(
    val lowStockCount: Int = 0,
    val dailySalesAmount: Double = 0.0,
    val monthlySalesAmount: Double = 0.0,
    val totalDebtAmount: Double = 0.0,
    val totalRevenue: Double = 0.0,
    val totalExpenses: Double = 0.0
)
