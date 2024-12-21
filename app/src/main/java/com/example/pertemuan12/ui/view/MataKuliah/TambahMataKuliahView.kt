package com.example.pertemuan12.ui.view.MataKuliah

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.ui.Navigation.Alamatnavigasi
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.FormErrorStateMataKuliah
import com.example.pertemuan12.ui.viewmodel.MataKuliahEvent
import com.example.pertemuan12.ui.viewmodel.MataKuliahUIState
import com.example.pertemuan12.ui.viewmodel.MataKuliahViewModel
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI
import kotlinx.coroutines.launch

object DestinasiTambah : Alamatnavigasi {
    override val route: String = "tambah_matakuliah"
}

@Composable
fun InsertMataKuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory)
) {
    val uiStateMataKuliah = viewModel.uiStateMataKuliah // Get UI state from ViewModel

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observe snackbarMessage changes
    LaunchedEffect(uiStateMataKuliah.snackBarMessageMataKuliah) {
        uiStateMataKuliah.snackBarMessageMataKuliah?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Show Snackbar
                viewModel.resetSnackBarMessageMataKuliah() // Reset message
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Show snackbar in scaffold
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            CustomTopAppBar(
                onBack = onBack,
//                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )
            // Body of the form
            InsertBodyMataKuliah(
                uiState = uiStateMataKuliah,
                onValueChange = { updateEvent -> viewModel.updateStateMataKuliah(updateEvent) },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveDataMataKuliah() // Save data
                    }
                    onNavigate() // Navigate after save
                },
                dosenList = uiStateMataKuliah.dosenList // Pass the list of dosen
            )
        }
    }
}

@Composable
fun InsertBodyMataKuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahUIState,
    onClick: () -> Unit,
    dosenList: List<Dosen>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList // Pass the dosenList to FormMataKuliah
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorStateMataKuliah = FormErrorStateMataKuliah(),
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>,
) {
    var chosenDropdown by remember { mutableStateOf(mataKuliahEvent.dosenPengampu) } // Default to the current value
    var expanded by remember { mutableStateOf(false) }

    val jenisPeminatan = listOf("Peminatan", "Wajib")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = { onValueChange(mataKuliahEvent.copy(kode = it)) },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(text = errorState.kode ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = { onValueChange(mataKuliahEvent.copy(nama = it)) },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") }
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks,
            onValueChange = { onValueChange(mataKuliahEvent.copy(sks = it)) },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") }
        )
        Text(text = errorState.sks ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester,
            onValueChange = { onValueChange(mataKuliahEvent.copy(semester = it)) },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") }
        )
        Text(text = errorState.semester ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            jenisPeminatan.forEach{jp ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mataKuliahEvent.jenisMataKulih == jp,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenisMataKulih = jp))
                        },
                    )
                    Text(
                        text = jp,
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = chosenDropdown,
                onValueChange = { /* Cannot be changed manually */ },
                label = { Text("Pilih Dosen Pengampu") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand menu"
                    )
                },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                isError = errorState.dosenPengampu != null
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        onClick = {
                            chosenDropdown = dosen.nama
                            expanded = false
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen.nama))
                        },
                        text = { Text(text = dosen.nama) }
                    )
                }
            }
        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)
    }
}
