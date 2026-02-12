package com.example.mosalisapp.ui.screens.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.domain.model.User
import com.example.mosalisapp.ui.componnent.AuthTextField
import com.example.mosalisapp.ui.theme.NeonIndication
import org.koin.androidx.compose.koinViewModel



@Composable
fun LoginScreen(
    onLoginSuccess: (User?) -> Unit,
    onNavigateToSignUp: () -> Unit,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(if (isPressed) 0.97f else 1f, label = "scale")

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onLoginSuccess((authState as AuthState.Authenticated).user)
            authViewModel.resetAuthState()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFFE8F3FF), Color(0xFFF4F8FC))))) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bon retour !", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = Color(0xFF0D253C))
            Text("Connectez-vous pour continuer", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)

            Spacer(modifier = Modifier.height(48.dp))

            AuthTextField(value = email, onValueChange = { email = it }, label = "Adresse Email", leadingIcon = Icons.Default.Email, keyboardType = KeyboardType.Email)
            Spacer(modifier = Modifier.height(16.dp))
            AuthTextField(value = password, onValueChange = { password = it }, label = "Mot de passe", leadingIcon = Icons.Default.Lock, keyboardType = KeyboardType.Password, isPassword = true, isPasswordVisible = isPasswordVisible, onPasswordVisibilityChange = { isPasswordVisible = !isPasswordVisible })

            Spacer(modifier = Modifier.height(32.dp))

            // BOUTON AVEC DÉGRADÉ HORIZONTAL ANIMÉ
            val buttonShape = MaterialTheme.shapes.extraLarge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .graphicsLayer { scaleX = scale; scaleY = scale }
                    .background(Color.White, buttonShape)
                    .indication(interactionSource, NeonIndication(buttonShape, 2.5.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple(color = Color.LightGray),
                        enabled = authState !is AuthState.Loading
                    ) { authViewModel.login(email, password) },
                contentAlignment = Alignment.Center
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(color = Color(0xFF30C0D8), modifier = Modifier.size(24.dp))
                } else {
                    Text("Se connecter", color = Color(0xFF0D253C), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                Text("Pas encore de compte ? ", color = Color.Gray)
                Text("Inscrivez-vous", color = Color(0xFF5A9DFF), fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onNavigateToSignUp() })
            }
        }
    }
}