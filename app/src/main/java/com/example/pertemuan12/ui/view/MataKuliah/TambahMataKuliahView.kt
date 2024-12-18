package com.example.pertemuan12.ui.view.MataKuliah



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Insert
import com.example.pertemuan12.ui.Navigation.Alamatnavigasi
import com.example.pertemuan12.ui.costumwidget.CustomTopAppBar
import com.example.pertemuan12.ui.viewmodel.FormErrorStateMataKuliah
import com.example.pertemuan12.ui.viewmodel.MataKuliahEvent
import com.example.pertemuan12.ui.viewmodel.MataKuliahUIState
import com.example.pertemuan12.ui.viewmodel.MataKuliahViewModel
import com.example.pertemuan12.ui.viewmodel.PenyediaViewModelProdiTI
import com.example.pertemuan12.ui.widget.DynamicSelectedTextField

import kotlinx.coroutines.launch
import org.w3c.dom.Text

object DestinasiTambah : Alamatnavigasi {
    override val route: String = "tambah_matakuliah"
}
@Composable
fun InsertMataKuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier =Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModelProdiTI.Factory),  // Inisialisasi View Model

){
    val  uiState = viewModel.uiStateMataKuliah // Ambil UI state dari view model
    val snackbarHostState = remember { SnackbarHostState() } //snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Obesrvasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessageMataKuliah) {
        uiState.snackBarMessageMataKuliah?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                viewModel.resetSnackBarMessageMataKuliah()
            }
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)} //tempatkan snackbar di scaffold
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa"
            )
            // Isi Body
            InsertBodyMataKuliah (
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateStatematakuliah(updateEvent) // Update event di view model
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveDataMataKuliah() // simpan data
                    }
                    onNavigate()
                }
            )
        }

    }
}

@Composable
fun InsertBodyMataKuliah(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahUIState,
    onClick: () -> Unit
){
    Column(
        modifier= Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa (
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
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
fun FormMahasiswa(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorStateMataKuliah = FormErrorStateMataKuliah(),
    modifier: Modifier = Modifier,
    dosenList: List<String> = listOf("Dosen A", "Dosen B", "Dosen C") // Contoh daftar dosen
) {
    var chosenDropdown by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // TextField untuk kode
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(kode = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(text = errorState.kode ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        // TextField untuk kode
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        // TextField untuk kode
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(sks = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(text = errorState.sks ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        // TextField untuk kode
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(semester = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(text = errorState.semester ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        // TextField untuk kode
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.jenisMataKulih,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(jenisMataKulih = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.jenisMataKulih != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(text = errorState.jenisMataKulih ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown untuk memilih dosen
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = chosenDropdown,
                onValueChange = { /* Tidak bisa diubah manual */ },
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
                            chosenDropdown = dosen
                            expanded = false
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen))
                        },
                        text = { Text(text = dosen) }
                    )
                }
            }
        }

        // Pesan error untuk dosen
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)
    }
}
