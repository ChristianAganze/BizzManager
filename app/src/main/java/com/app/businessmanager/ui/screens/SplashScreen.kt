package com.app.businessmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.businessmanager.domain.model.Role
import com.app.businessmanager.ui.navigation.Screen
import com.app.businessmanager.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        delay(2000) // Visual grace period
        if (currentUser != null) {
            if (currentUser?.role == Role.OWNER) {
                navController.navigate(Screen.OwnerDashboard) {
                    popUpTo(Screen.Splash) { inclusive = true }
                }
            } else {
                navController.navigate(Screen.WorkerDashboard) {
                    popUpTo(Screen.Splash) { inclusive = true }
                }
            }
        } else {
            navController.navigate(Screen.Welcome) {
                popUpTo(Screen.Splash) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Mosalis App",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
