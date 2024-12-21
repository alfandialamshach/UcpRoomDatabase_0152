package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.repository.RepositoryMataKuliah
import kotlinx.coroutines.launch

// ViewModel for MataKuliah
class MataKuliahViewModel(
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    // UI State for MataKuliah
    var uiStateMataKuliah by mutableStateOf(MataKuliahUIState())
        private set

    // MataKuliah List
    var mataKuliahList by mutableStateOf<List<MataKuliah>>(emptyList())
        private set

    // Dosen List
    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    init {
        // Fetch MataKuliah from repository
        viewModelScope.launch {
            repositoryMataKuliah.getAllMataKuliah().collect { mataKuliahList ->
                this@MataKuliahViewModel.mataKuliahList = mataKuliahList
            }
        }

        // Fetch Dosen from repository
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { dosenList ->
                this@MataKuliahViewModel.dosenList = dosenList
                updateUiState() // Update UI State after fetching Dosen
            }
        }
    }

    // Update the state for MataKuliah input fields
    fun updateStateMataKuliah(mataKuliahEvent: MataKuliahEvent) {
        uiStateMataKuliah = uiStateMataKuliah.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    // Validate input fields for MataKuliah
    fun validateFieldsTambah(): Boolean {
        val eventMataKuliah = uiStateMataKuliah.mataKuliahEvent
        val errorStateMatakuliah = FormErrorStateMataKuliah(
            kode = if (eventMataKuliah.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (eventMataKuliah.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (eventMataKuliah.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (eventMataKuliah.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenisMataKulih = if (eventMataKuliah.jenisMataKulih.isNotEmpty()) null else "Jenis Mata Kuliah tidak boleh kosong",
            dosenPengampu = if (eventMataKuliah.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        // Update state untuk error message
        uiStateMataKuliah = uiStateMataKuliah.copy(isEntryValid = errorStateMatakuliah)

        // Pastikan form valid jika tidak ada pesan error
        return errorStateMatakuliah.isValid()
    }


    // Save MataKuliah data to repository
    fun saveDataMataKuliah() {
        val currentEventMataKuliah = uiStateMataKuliah.mataKuliahEvent

        // Validasi form sebelum menyimpan
        if (validateFieldsTambah()) {

            // Jika validasi sukses, simpan data ke repository
            viewModelScope.launch {
                try {
                    // Menyimpan data ke repository
                    repositoryMataKuliah.insertMataKuliah(currentEventMataKuliah.toMataKuliahEntity())

                    // Jika penyimpanan berhasil, reset form dan tampilkan pesan sukses
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data berhasil disimpan", // Pesan berhasil
                        mataKuliahEvent = MataKuliahEvent(), // Reset form
                        isEntryValid = FormErrorStateMataKuliah() // Reset error state
                    )
                } catch (e: Exception) {
                    // Jika terjadi error, tampilkan pesan error
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data gagal disimpan: ${e.message}"
                    )
                }
            }
        } else {
            // Jika validasi gagal, tampilkan pesan error
            uiStateMataKuliah = uiStateMataKuliah.copy(
                snackBarMessageMataKuliah = "Input tidak valid. Periksa kembali data Anda"
            )
        }
    }


    // Reset snackbar message after it's displayed
    fun resetSnackBarMessageMataKuliah() {
        uiStateMataKuliah = uiStateMataKuliah.copy(snackBarMessageMataKuliah = null)
    }

    // Update UI state with Dosen list
    private fun updateUiState() {
        uiStateMataKuliah = uiStateMataKuliah.copy(dosenList = dosenList)
    }
}

// UI State for MataKuliah
data class MataKuliahUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorStateMataKuliah = FormErrorStateMataKuliah(),
    val snackBarMessageMataKuliah: String? = null,
    val dosenList: List<Dosen> = emptyList()
)

// Error state for MataKuliah form validation
data class FormErrorStateMataKuliah(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenisMataKulih: String? = null,
    val dosenPengampu: String? = null
) {
    fun isValid(): Boolean {
        return kode == null && nama == null && sks == null &&
                semester == null && jenisMataKulih == null && dosenPengampu == null
    }
}


// Data class to hold MataKuliah form input values
data class MataKuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenisMataKulih: String = "",
    val dosenPengampu: String = ""
)

// Extension function to convert MataKuliahEvent to MataKuliah entity
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenisMataKulih = jenisMataKulih,
    dosenPengampu = dosenPengampu
)

