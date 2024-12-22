package com.example.pertemuan12.ui.costumwidget


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomBottomAppBar(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.height(64.dp), // Adjusted to make it visually similar to the image
        containerColor = Color.Transparent, // Makes the background transparent
        contentColor = Color.White,
        tonalElevation = 0.dp // Removes elevation for a flat look
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround, // Space items evenly
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp) // Adjusted icon size to look proportionate
                )
            }

            IconButton(onClick = onHomeClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.Blue,
                    modifier = Modifier.size(36.dp) // Adjusted icon size
                )
            }
        }
    }
}
