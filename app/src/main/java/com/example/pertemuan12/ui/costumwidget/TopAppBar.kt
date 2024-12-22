package com.example.pertemuan12.ui.costumwidget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode

@Composable
fun CustomTopAppBar(
    judul: String,
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State untuk mengontrol apakah menu dropdown ditampilkan
    var showMenu by remember { mutableStateOf(false) }

    // State untuk animasi gradien latar belakang
    val gradientColors by rememberUpdatedState(
        if (showMenu) listOf(Color(0xFF1976D2), Color(0xFF64B5F6))
        else listOf(Color(0xFF2196F3), Color(0xFF42A5F5))
    )

    val gradientBrush = remember(gradientColors) {
        Brush.horizontalGradient(
            colors = gradientColors,
            startX = 0f,
            endX = 1000f,
            tileMode = TileMode.Clamp
        )
    }

    // Struktur TopAppBar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(gradientBrush) // Latar belakang dengan gradien dinamis
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol menu dropdown
            Box {
                IconButton(
                    onClick = { showMenu = !showMenu },
                    modifier = Modifier
                        .animateScaleOnPress() // Animasi ikon interaktif
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Dosen") },
                        onClick = {
                            showMenu = false
                            onDosenClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Mata Kuliah") },
                        onClick = {
                            showMenu = false
                            onMataKuliahClick()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Spasi untuk membuat judul berada di tengah

            // Judul halaman dengan animasi
            AnimatedVisibility(
                visible = true, // Anda bisa mengganti sesuai kondisi yang Anda butuhkan
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                AnimatedFadingTextTopBar(
                    text = judul,

                )
            }
        }
    }
}

// Ekstensi untuk animasi skala pada ikon saat ditekan
@Composable
fun Modifier.animateScaleOnPress(): Modifier = composed {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (pressed) 0.9f else 1f)

    this
        .scale(scale)
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    pressed = true
                    tryAwaitRelease()
                    pressed = false
                }
            )
        }
}


@Composable
fun AnimatedFadingTextTopBar(text: String) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFFFFEB3B).copy(alpha = alpha), // Mengatur transparansi menggunakan alpha
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

