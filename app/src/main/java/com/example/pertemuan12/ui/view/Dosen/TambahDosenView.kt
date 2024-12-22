package com.example.pertemuan12.ui.view.Dosen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.ui.Navigation.Alamatnavigasi
import com.example.pertemuan12.ui.costumwidget.CustomBottomAppBar
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.DosenEvent
import com.example.pertemuan12.ui.viewmodel.DosenUIState
import com.example.pertemuan12.ui.viewmodel.DosenViewModel
import com.example.pertemuan12.ui.viewmodel.FormErrorStateDosen
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiTambahDosen : Alamatnavigasi {
    override val route: String = "Tambah_Dosen"
}
@Composable

fun TambahDosenView(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    viewModel: DosenViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory),  // Inisialisasi View Model

    ) {
    val uiState = viewModel.uiStateDosen // Ambil UI state dari view model
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                viewModel.resetSnackBarMessageDosen()
            }
        }
    }

    Scaffold(
        modifier = modifier,

        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Tempatkan snackbar di scaffold
        bottomBar = { // Menambahkan CustomBottomAppBar di bawah
            CustomBottomAppBar(
                onBackClick = onBackClick,
                onHomeClick = onHomeClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            CustomTopAppBar(
                onDosenClick = onDosenClick,
                onMataKuliahClick = onMataKuliahClick,
                judul = "Tambah Dosen ProdiTI"
            )

            InsertBodyDosen(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateStateDosen(updateEvent) // Update event di view model
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFieldsDosen()) {
                            viewModel.saveData()
                            delay(500)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}



@Composable
fun InsertBodyDosen(
    modifier: Modifier = Modifier,
    onValueChange: (DosenEvent) -> Unit,
    uiState: DosenUIState,
    onClick: () -> Unit
){
    Column(
        modifier= Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDosen (
            dosenEvent = uiState.dosenEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {onClick()},
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}
@Composable
fun FormDosen(
    dosenEvent: DosenEvent = DosenEvent(),
    onValueChange: (DosenEvent) -> Unit,
    errorState: FormErrorStateDosen = FormErrorStateDosen(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("Laki - laki", "Perempuan")


    Column (
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nama,
            onValueChange = {
                onValueChange(dosenEvent.copy(nama = it))
            },
            label = { Text("Nama")},
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama")},
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nidn, onValueChange = {
                onValueChange(dosenEvent.copy( nidn = it))
            },
            label = { Text("NIDN")},
            isError = errorState.nidn != null,
            placeholder = { Text("Masukkan NIDN")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.nidn ?: "", color = Color.Red)


        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            jenisKelamin.forEach{jk ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = dosenEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(dosenEvent.copy(jenisKelamin = jk))
                        },
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }
        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )

    }
}