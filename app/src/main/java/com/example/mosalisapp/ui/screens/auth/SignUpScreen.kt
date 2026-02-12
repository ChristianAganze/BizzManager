package com.example.mosalisapp.ui.screens.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mosalisapp.ui.componnent.AuthTextField
import com.example.mosalisapp.ui.theme.NeonIndication
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onSignUpSuccess()
            authViewModel.resetAuthState()
        }
    }

    // Animation pour l'effet Shimmer du texte (pendant le chargement)
    val shimmerTranslateAnim = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by shimmerTranslateAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing)),
        label = "shimmerOffset"
    )

    val backgroundColor = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F3FF), Color(0xFFF4F8FC))
    )

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                "Créer un compte business",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D253C)
            )
            Text(
                "Commencez par vos accès personnels",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))

            AuthTextField(
                value = userName,
                onValueChange = { userName = it },
                label = "Nom complet",
                leadingIcon = Icons.Default.Person,
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Mot de passe",
                leadingIcon = Icons.Default.Lock,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                isPasswordVisible = isPasswordVisible,
                onPasswordVisibilityChange = { isPasswordVisible = !isPasswordVisible },
            )

            Spacer(modifier = Modifier.height(40.dp))

            // BOUTON AVEC BORDURE ANIMÉE ET SHIMMER
            val buttonShape = MaterialTheme.shapes.extraLarge
            val isLoading = authState is AuthState.Loading

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
                        enabled = !isLoading
                    ) {
                        authViewModel.signUp(email, password, userName)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    // Texte scintillant (Shimmer) pendant le chargement
                    val shimmerBrush = Brush.linearGradient(
                        colors = listOf(Color.Gray.copy(0.3f), Color.White, Color.Gray.copy(0.3f)),
                        start = Offset(shimmerOffset, shimmerOffset),
                        end = Offset(shimmerOffset + 200f, shimmerOffset + 200f)
                    )
                    Text(
                        "Création en cours...",
                        style = androidx.compose.ui.text.TextStyle(brush = shimmerBrush),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        "Continuer",
                        color = Color(0xFF0D253C),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text("Déjà un compte ? ", color = Color.Gray)
                Text(
                    "Connectez-vous",
                    color = Color(0xFF5A9DFF),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }

            if (authState is AuthState.Error) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = (authState as AuthState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}