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
import com.example.pertemuan12.ui.costumwidget.CustomBottomAppBar
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.DosenViewModel
import com.example.pertemuan12.ui.viewmodel.FormErrorStateMataKuliah
import com.example.pertemuan12.ui.viewmodel.MataKuliahEvent
import com.example.pertemuan12.ui.viewmodel.MataKuliahUIState
import com.example.pertemuan12.ui.viewmodel.MataKuliahViewModel
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiTambah : Alamatnavigasi {
    override val route: String = "tambah_matakuliah"
}

@Composable
fun InsertMataKuliahView(
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory),
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Show snackbar in scaffold
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
                onMataKuliahClick =onMataKuliahClick,
                judul = "Tambah MataKuliah"
            )
            // Body of the form
            InsertBodyMataKuliah(
                uiState = uiStateMataKuliah,
                dosenList = uiStateMataKuliah.dosenList,
                onValueChange = { updateEvent -> viewModel.updateStateMataKuliah(updateEvent) },
                onClick = {
                    coroutineScope.launch {
//                        viewModel.saveDataMataKuliah() // Save data
                        if (viewModel.validateFieldsTambah()){
                            viewModel.saveDataMataKuliah()
                            delay(500)
                            withContext(Dispatchers.Main){
                                onNavigate() // Navigate after save
                            }
                        }
                    }

                },
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
            dosenList = dosenList //  dosenList to FormMataKuliah
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
            label = { Text("Kode MataKuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode MataKuliah") }
        )
        Text(text = errorState.kode ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = { onValueChange(mataKuliahEvent.copy(nama = it)) },
            label = { Text("Nama MataKuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama MataKuliah") }
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks,
            onValueChange = { onValueChange(mataKuliahEvent.copy(sks = it)) },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan SKS MataKuliah") }
        )
        Text(text = errorState.sks ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester,
            onValueChange = { onValueChange(mataKuliahEvent.copy(semester = it)) },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester MataKuliah") }
        )
        Text(text = errorState.semester ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Jenis MataKuliah")
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
                        text = { Text(text = dosen.nama) },

                    )
                }
            }
        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)
    }
}
