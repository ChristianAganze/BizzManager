package com.app.businessmanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.businessmanager.ui.navigation.Screen
import com.app.businessmanager.ui.theme.MosalisAppTheme
import com.app.businessmanager.ui.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            com.app.businessmanager.ui.theme.MosalisTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val currentUser by authViewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(navController, authViewModel)
        }
        composable<Screen.Welcome> {
            WelcomeScreen(navController)
        }
        composable<Screen.Login> {
            LoginScreen(navController, authViewModel)
        }
        composable<Screen.Register> {
            RegisterScreen(navController, authViewModel)
        }
        composable<Screen.OwnerDashboard> {
            OwnerDashboardScreen(navController)
        }
        composable<Screen.WorkerDashboard> {
            WorkerDashboardScreen(navController)
        }
        composable<Screen.NewSale> {
            SaleFormScreen(navController)
        }
        composable<Screen.NewDebt> {
            DebtFormScreen(navController)
        }
        composable<Screen.NewExpense> {
            ExpenseFormScreen(navController)
        }
        composable<Screen.ClientList> {
            ClientListScreen(navController)
        }
        composable<Screen.AddClient> {
            AddClientScreen(navController)
        }
        composable<Screen.WorkerList> {
            WorkerListScreen(navController)
        }
        composable<Screen.AddWorker> {
            AddWorkerScreen(navController)
        }
    }
}
