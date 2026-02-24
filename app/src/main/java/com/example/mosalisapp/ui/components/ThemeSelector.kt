package com.example.mosalisapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.viewmodel.AppTheme
import com.example.mosalisapp.ui.viewmodel.ThemeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ThemeSelector(
    viewModel: ThemeViewModel = koinViewModel()
 ) {
    val currentTheme by viewModel.theme.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ThemeOption(
            icon = Icons.Default.LightMode,
            label = "Clair",
            isSelected = currentTheme == AppTheme.LIGHT,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.setTheme(AppTheme.LIGHT) }
        )
        ThemeOption(
            icon = Icons.Default.DarkMode,
            label = "Sombre",
            isSelected = currentTheme == AppTheme.DARK,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.setTheme(AppTheme.DARK) }
        )
        ThemeOption(
            icon = Icons.Default.SettingsSuggest,
            label = "Système",
            isSelected = currentTheme == AppTheme.SYSTEM,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.setTheme(AppTheme.SYSTEM) }
        )
    }
}
