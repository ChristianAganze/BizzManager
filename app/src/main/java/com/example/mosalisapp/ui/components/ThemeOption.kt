package com.example.mosalisapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mosalisapp.ui.theme.AntigravityCyan
import com.example.mosalisapp.ui.theme.DeepIndigo
import com.example.mosalisapp.ui.theme.GlassWhite
import com.example.mosalisapp.ui.theme.GlowCyan
import com.example.mosalisapp.ui.theme.SlateGrey

@Composable
fun ThemeOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) GlowCyan else DeepIndigo
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) AntigravityCyan else GlassWhite
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (isSelected) AntigravityCyan else SlateGrey
            )
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) AntigravityCyan else SlateGrey
            )
        }
    }
}
