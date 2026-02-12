package com.example.mosalisapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.mosalisapp.ui.screens.*
import com.example.mosalisapp.ui.screens.auth.*
import com.example.mosalisapp.ui.screens.company.CreateCompanyScreen
import com.example.mosalisapp.ui.screens.company.UserScreen
import org.koin.compose.viewmodel.koinViewModel

sealed interface AppRoute {
    object Splash : AppRoute
    object Login : AppRoute
    object SignUp : AppRoute
    object OwnerDashboard : AppRoute
    object WorkerDashboard : AppRoute
    object Users : AppRoute
    object Products : AppRoute
    object Sales : AppRoute
    object AjoutSales: AppRoute
    object Expenses : AppRoute
    object Debts : AppRoute
    object Agenda : AppRoute
    object Campany : AppRoute // Création d'entreprise
    object UserProfile : AppRoute
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
                SplashWelcomeScreen(
                    onFinishSplash = {
                        val currentUser = authViewModel.currentUser.value
                        backStack.clear()
                        if (currentUser != null) {
                            if (currentUser.role == "OWNER") {
                                // Sécurité : redirection si l'entreprise n'est pas encore créée
                                if (currentUser.businessId.isNullOrEmpty()) {
                                    backStack.add(AppRoute.Campany)
                                } else {
                                    backStack.add(AppRoute.OwnerDashboard)
                                }
                            } else {
                                backStack.add(AppRoute.WorkerDashboard)
                            }
                        } else {
                            backStack.add(AppRoute.Login)
                        }
                    }
                )
            }

            entry<AppRoute.Login> {
                LoginScreen(
                    onNavigateToSignUp = { backStack.add(AppRoute.SignUp) },
                    onLoginSuccess = { user ->
                        backStack.clear()
                        if (user?.role == "OWNER") {
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
                SignUpScreen(
                    onNavigateToLogin = { backStack.removeLastOrNull() },
                    onSignUpSuccess = {
                        backStack.clear()
                        backStack.add(AppRoute.Campany)
                    }
                )
            }

            entry<AppRoute.Campany> {
                CreateCompanyScreen(
                    onSuccess = {
                        backStack.clear()
                        backStack.add(AppRoute.OwnerDashboard)
                    }
                )
            }

            entry<AppRoute.OwnerDashboard> {
                OwnerDashboardScreen(
                    onNavigateToUsers = { backStack.add(AppRoute.Users) },
                    onNavigateToProducts = { backStack.add(AppRoute.Products) },
                    onNavigateToSales = { backStack.add(AppRoute.Sales) },
                    onNavigateToExpenses = { backStack.add(AppRoute.Expenses) },
                    onNavigateToDebts = { backStack.add(AppRoute.Debts) },
                    onNavigateToAgenda = { backStack.add(AppRoute.Agenda) },
                    onNavigateToProfile = { backStack.add(AppRoute.UserProfile) },
                    onLogout = {
                        authViewModel.logout()
                        backStack.clear()
                        backStack.add(AppRoute.Login)
                    }
                )
            }

            entry<AppRoute.WorkerDashboard> {
                WorkerDashboardScreen(
                    onNavigateToSales = { backStack.add(AppRoute.Sales) },
                    onNavigateToProducts = { backStack.add(AppRoute.Products) },
                    onNavigateToProfile = { backStack.add(AppRoute.UserProfile) },
                    onLogout = {
                        authViewModel.logout()
                        backStack.clear()
                        backStack.add(AppRoute.Login)
                    }
                )
            }

            entry<AppRoute.Users> {
                UserScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.Products> {
                ProductsScreen(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                })
            }

            entry<AppRoute.Sales> {
                SalesScreen(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onNavigateToAddSale = { backStack.add(AppRoute.AjoutSales) }
                )
            }

            entry<AppRoute.Expenses> {
                ExpensesScreen(onNavigateBack = { backStack.removeLastOrNull() })
            }

            entry<AppRoute.Debts> {
                DebtsScreen(onNavigateBack = { backStack.removeLastOrNull() })
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
        }
    )
}