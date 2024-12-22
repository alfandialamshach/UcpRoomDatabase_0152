package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.repository.RepositoryMataKuliah
import com.example.pertemuan12.ui.Navigation.DestinasiUpdateMataKuliah

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    // Variabel Dosen List
    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set
    // update UI state MataKuliah
    var updateUiStateMataKuliah by mutableStateOf(MataKuliahUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMataKuliah.KODE])

    init {
        // mengambil data MataKuliah berdasarkan kode
        viewModelScope.launch {
            updateUiStateMataKuliah = repositoryMataKuliah.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMataKuliah()
        }

        // Mengambil data Dosen dari repository dosen
        viewModelScope.launch {
            val dosenListFromRepo = repositoryDosen.getAllDosen().first() // Fetch Dosen
            dosenList = dosenListFromRepo
            updateUiStateMataKuliah = updateUiStateMataKuliah.copy(dosenList = dosenList) // Update UI state with dosenList
        }
    }


    // Update the state di MataKuliah input fields
    fun updateStateMataKuliah(mataKuliahEvent: MataKuliahEvent) {
        println("Update Event: $mataKuliahEvent")
        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(mataKuliahEvent = mataKuliahEvent)
    }
    // Validate input fields dalam MataKuliah harus tidak boleh kosong
    fun validateField(): Boolean {
        val event = updateUiStateMataKuliah.mataKuliahEvent
        val errorState = FormErrorStateMataKuliah(
            kode = if (event.kode.isNotEmpty()) null else "Kode Tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS Tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Tidak boleh kosong",
            jenisMataKulih = if (event.jenisMataKulih.isNotEmpty()) null else "Jenis Mata Kuliah Tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu Tidak boleh kosong"
        )

        // Update state untuk error message
        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(isEntryValid = errorState)
        // Pastikan form valid jika tidak ada pesan error
        return errorState.isValid()
    }

    // Save MataKuliah data to repository matakuliah
    fun updateData() {
        val  currentEvent = updateUiStateMataKuliah.mataKuliahEvent
        // Validasi form sebelum menyimpan
        if (validateField()) {
            // Jika validasi sukses, simpan data ke repository
            viewModelScope.launch {
                try {
                    // Menyimpan data ke repository
                    repositoryMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    // Jika penyimpanan berhasil, reset form dan tampilkan pesan sukses
                    updateUiStateMataKuliah = updateUiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data Berhasil Diupdate", // Pesan berhasil
                        mataKuliahEvent = MataKuliahEvent(), // Reset form
                        isEntryValid = FormErrorStateMataKuliah() // Reset error state
                    )
                    println("snackBarMessage diatur: ${updateUiStateMataKuliah.snackBarMessageMataKuliah}")
                }catch (e: Exception) {
                    // Jika terjadi error, tampilkan pesan error
                    updateUiStateMataKuliah =updateUiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data gaga; diupdate"
                    )
                }
            }

        // Jika validasi gagal, tampilkan pesan error
        } else {
            updateUiStateMataKuliah = updateUiStateMataKuliah.copy(
                snackBarMessageMataKuliah = " Data gagal diupdate"
            )
        }
    }
    // Setel ulang pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(snackBarMessageMataKuliah = null)
    }
}

// Convert MataKuliah to UI State
fun MataKuliah.toUIStateMataKuliah(): MataKuliahUIState = MataKuliahUIState(
    mataKuliahEvent = this.toDetailUiEvent() // Convert MataKuliah to UI Event
)
