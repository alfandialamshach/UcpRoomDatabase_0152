package com.example.pertemuan12.ui.view.MataKuliah


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.ui.costumwidget.CustomBottomAppBar
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI

import kotlinx.coroutines.launch

@Composable
fun HomeMataKuliahView(
    viewModel: HomeMataKuliahViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory),
    onAddMhs: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onDosenClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,

    onMataKuliahClick: () -> Unit,
    modifier : Modifier = Modifier
) {
    Scaffold (
        modifier = modifier.padding(16.dp),
        bottomBar = { // Menambahkan CustomBottomAppBar di bawah
            CustomBottomAppBar(
                onBackClick = onBackClick,
                onHomeClick = onHomeClick
            )
        },
        topBar = {
            CustomTopAppBar(
                judul = "Daftar MataKuliah ProdiTI",
                onDosenClick = onDosenClick,
                onMataKuliahClick =onMataKuliahClick,
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Tambahkan jarak dari bawah
                horizontalArrangement = Arrangement.Center, // Posisikan di tengah horizontal
                verticalAlignment = Alignment.CenterVertically
            ) {

                FloatingActionButton(
                    onClick = onAddMhs,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(start = 16.dp) // Beri jarak antar FAB

                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah MataKuliah",
                    )
                }
            }
        }
    ){innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeMataKuliahView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun BodyHomeMataKuliahView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiState.isloading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiState.isError -> {
            //Menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) // Tampilkan snackbar
                    }
                }
            }
        }
        homeUiState.listMataKuliah.isEmpty() -> {
            //Menampilkan pesan jika data kosong
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data matakuliah",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            //menampilkan daftar mahasiswa
            ListMataKuliah(
                listMataKuliah = homeUiState.listMataKuliah,
                onClick = {
                    onClick(it)
                    println(
                        it
                    )
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMataKuliah(
    listMataKuliah: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn (
        modifier = modifier
    ){
        items(
            items= listMataKuliah,
            itemContent = { matakuliah ->
                CardMataKuliah(
                    mataKuliah = matakuliah,
                    onClick = { onClick(matakuliah.kode)}
                )
            }
        )
    }
}

@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun CardMataKuliah(
    mataKuliah: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
    ){
        Column (
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ){
            Row (
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mataKuliah.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier .fillMaxWidth().padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mataKuliah.sks,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mataKuliah.dosenPengampu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}