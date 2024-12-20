package com.example.pertemuan12.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pertemuan12.R
import com.example.pertemuan12.ui.Navigation.Alamatnavigasi

object DestinasiHome : Alamatnavigasi {
    override val route: String = "Home"
}

@Composable
fun HomeView(
    onClickDosen: () -> Unit = {},
    onClickMataKuliah: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.umy),
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(30.dp))

            Column {
                Text(
                    text = "Hallo",
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Teknologi Informasi",
                    color = Color.Red,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topEnd = 50.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Menu",
                    color = Color.Red,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onClickDosen,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Dosen")
                }

                Button(
                    onClick = onClickMataKuliah,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 8.dp)
                ) {
                    Text("Mata Kuliah")
                }
            }
        }
    }
}
