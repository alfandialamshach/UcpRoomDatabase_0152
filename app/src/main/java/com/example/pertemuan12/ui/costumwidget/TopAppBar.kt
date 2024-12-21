package com.example.pertemuan12.ui.costumwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTopAppBar(
    judul: String,
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State untuk mengontrol apakah menu dropdown ditampilkan
    var showMenu by remember { mutableStateOf(false) }

    // Struktur TopAppBar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF2196F3)) // Warna latar belakang biru
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
                IconButton(onClick = { showMenu = true }) {
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

            // Judul halaman
            Text(
                text = judul,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}
