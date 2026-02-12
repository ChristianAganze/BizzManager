package com.example.mosalisapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.theme.MosalisAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MosalisAppTheme {
                /*
                EnregistreScreen(
                    onSuccess = {
                        println("Inscription réussie")
                    },
                  onBack = {
                      println("Retour")

                  }
                )

                ConnexionScreen(
                    onSuccess = {
                        println("Connexion réussie")
                    },
                    onNavigateToRegister = {

                    }
                )
*/
            AppMosali()

            }
        }
    }
}

@Composable
fun AppMosali(){
    val backStack = remember { mutableStateListOf<AppRoute>(AppRoute.Splash) }
    BackHandler(enabled = backStack.size > 1) {
        backStack.removeFirstOrNull()
    }
    AppNavHost(
        backStack = backStack,
        padding = PaddingValues(0.dp)
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MosalisAppTheme {
        AppMosali()

    }
}