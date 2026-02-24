package com.example.mosalisapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.mosalisapp.ui.screens.*
import com.example.mosalisapp.ui.viewmodel.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

sealed interface AppRoute {
    object Splash : AppRoute
    object Welcome : AppRoute
    object Login : AppRoute
    object SignUp : AppRoute
    object OwnerDashboard : AppRoute
    object WorkerDashboard : AppRoute
    object Users : AppRoute
    object Clients : AppRoute
    object Products : AppRoute
    object Sales : AppRoute
    object AjoutSales: AppRoute
    object Expenses : AppRoute
    object Debts : AppRoute
    object Agenda : AppRoute
    object Campany : AppRoute // Création d'entreprise
    object UserProfile : AppRoute
    data class ClientDetail(val clientId: String): AppRoute
    object AjoutClient: AppRoute
}

@Composable
fun AppNavHost(
    backStack: MutableList<AppRoute>,
    padding: PaddingValues,
    authViewModel: AuthViewModel = koinViewModel()
) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = Modifier.padding(padding),
        entryProvider = entryProvider {

            entry<AppRoute.Splash> {
                SplashScreen(
                    onNavigateToWelcome = { backStack.add(AppRoute.Welcome) },
                    onNavigateToDashboard = { backStack.add(AppRoute.OwnerDashboard) },
                    onNavigateToWorkerDashboard = { backStack.add(AppRoute.WorkerDashboard) }
                )
            }

            entry<AppRoute.Welcome> {
                WelcomeScreen(
                    onNavigateToLogin = { backStack.add(AppRoute.Login) },
                    onNavigateToSignUp = { backStack.add(AppRoute.SignUp) }
                )
            }

            entry<AppRoute.Login> {
                LoginScreen(
                    onNavigateToSignUp = { backStack.add(AppRoute.SignUp) },
                    onLoginSuccess = { user ->
                        backStack.clear()
                        if (user?.role?.name == "OWNER") {
                            if (user.businessId.isNullOrEmpty()) {
                                backStack.add(AppRoute.Campany)
                            } else {
                                backStack.add(AppRoute.OwnerDashboard)
                            }
                        } else {
                            backStack.add(AppRoute.WorkerDashboard)
                        }
                    }
                )
            }

            entry<AppRoute.SignUp> {
                RegisterScreen(
                    onNavigateToLogin = { backStack.removeLastOrNull() },
                    onRegisterSuccess = {
                        backStack.clear()
                        backStack.add(AppRoute.Campany)
                    }
                )
            }

            entry<AppRoute.Campany> {
                BusinessRegistrationScreen(
                    onRegistrationSuccess = {
                        backStack.clear()
                        backStack.add(AppRoute.OwnerDashboard)
                    }
                )
            }

            entry<AppRoute.OwnerDashboard> {
                OwnerDashboardScreen(
                    onNavigateToWorkerList = { backStack.add(AppRoute.Users) },
                    onNavigateToClientList = { backStack.add(AppRoute.Clients) }
                )
            }

            entry<AppRoute.WorkerDashboard> {
                WorkerDashboardScreen(
                    onNavigateToSales = { backStack.add(AppRoute.Sales) },
                    onNavigateToExpenses = { backStack.add(AppRoute.Expenses) },
                    onNavigateToDebts = { backStack.add(AppRoute.Debts) },
                    onNavigateToProfile = { backStack.add(AppRoute.UserProfile) },
                    onLogout = {
                        authViewModel.logout()
                        backStack.clear()
                        backStack.add(AppRoute.Login)
                    }
                )
            }

            entry<AppRoute.Users> {
                WorkerListScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToAddWorker = { /* Logique d'ajout si besoin */ }
                )
            }

            entry<AppRoute.Clients> {
                ClientListScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToAddClient = { backStack.add(AppRoute.AjoutClient) },
                    onNavigateToClientDetail = { clientId ->
                        backStack.add(AppRoute.ClientDetail(clientId))
                    }
                )
            }

            entry<AppRoute.Products> {
                ProductsScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.Sales> {
                SalesScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToAddSale = { backStack.add(AppRoute.AjoutSales) }
                )
            }

            entry<AppRoute.Expenses> {
                ExpenseFormScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.Debts> {
                DebtFormScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.AjoutSales> {
                AddSaleScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.Agenda> {
                AgendaScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.UserProfile> {
                UserProfileScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onLogout = {
                        authViewModel.logout()
                        backStack.clear()
                        backStack.add(AppRoute.Login)
                    }
                )
            }

            entry<AppRoute.ClientDetail> { entry ->
                ClientDetailScreen(
                    clientId = entry.clientId,
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }

            entry<AppRoute.AjoutClient> {
                AddClientScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }
        }
    )
}
