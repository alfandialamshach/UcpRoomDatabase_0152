package com.example.pertemuan12.ui.view.Dosen

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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI

import kotlinx.coroutines.launch

@Composable
fun HomeDosenView(
    viewModel: HomeDosenViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory),
    onAddMhs: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onBack: () -> Unit,
    modifier : Modifier = Modifier
) {
    Scaffold (
        topBar = {
            CustomTopAppBar(
                judul = "Daftar Mahasiswa",
                showBackButton = true,
                onBack = onBack,

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMhs,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Mahasiswa",
                )
            }
        }
    ){innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyHomeMhsView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun BodyHomeMhsView(
    homeUiState: HomeUiStateDosen,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiState.isloadingDosen -> {
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
            LaunchedEffect(homeUiState.errorMessageDosen) {
                homeUiState.errorMessageDosen?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) // Tampilkan snackbar
                    }
                }
            }
        }
        homeUiState.listDosen.isEmpty() -> {
            //Menampilkan pesan jika data kosong
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data mahasiswa",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            //menampilkan daftar mahasiswa
            ListDosen(
                listDosen = homeUiState.listDosen,
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
fun ListDosen(
    listDosen: List<Dosen>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn (
        modifier = modifier
    ){
        items(
    items = listDosen,
    itemContent = { dosen ->
        CardMhs(
            dosen = dosen,
//            onClick = {} // Tidak melakukan apa-apa saat item diklik
        )
    }
)
    }
}

@OptIn (ExperimentalMaterial3Api::class)
@Composable
fun CardMhs(
    dosen: Dosen,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Column (
            modifier = Modifier.fillMaxWidth()
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = dosen.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Row (
                modifier = Modifier .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = dosen.nidn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = dosen.jenisKelamin,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}