package com.app.businessmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.businessmanager.ui.components.BusinessButton
import com.app.businessmanager.ui.components.BusinessTextField
import com.app.businessmanager.ui.navigation.Screen
import com.app.businessmanager.ui.viewmodel.AuthState
import com.app.businessmanager.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            // Navigation is handled by SplashScreen or MainActivity's Auth observer
            // but we can force it here if needed.
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Connexion",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        BusinessTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        BusinessTextField(
            value = password,
            onValueChange = { password = it },
            label = "Mot de passe"
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        if (authState is AuthState.Loading) {
            CircularProgressIndicator()
        } else {
            BusinessButton(
                text = "Se connecter",
                onClick = { viewModel.login(email, password) }
            )
        }
        
        if (authState is AuthState.Error) {
            Text(
                text = (authState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        
        TextButton(
            onClick = { navController.navigate(Screen.Register) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Pas de compte ? S'inscrire")
        }
    }
}
