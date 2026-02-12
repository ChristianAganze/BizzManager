package com.app.businessmanager.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    object Splash : Screen

    @Serializable
    object Welcome : Screen

    @Serializable
    object Login : Screen

    @Serializable
    object Register : Screen

    @Serializable
    object OwnerDashboard : Screen

    @Serializable
    object WorkerDashboard : Screen

    @Serializable
    object ClientList : Screen

    @Serializable
    data class ClientDetail(val clientId: String) : Screen

    @Serializable
    object ProductList : Screen

    @Serializable
    object SaleDetail : Screen

    @Serializable
    object NewSale : Screen

    @Serializable
    object NewDebt : Screen

    @Serializable
    object NewExpense : Screen

    @Serializable
    object AddClient : Screen

    @Serializable
    object WorkerList : Screen

    @Serializable
    object AddWorker : Screen
}
