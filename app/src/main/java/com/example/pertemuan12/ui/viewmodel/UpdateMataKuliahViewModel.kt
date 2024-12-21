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
    // Dosen List
    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    var updateUiStateMataKuliah by mutableStateOf(MataKuliahUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMataKuliah.KODE])

    init {
        // Fetch Mata Kuliah Data by Kode
        viewModelScope.launch {
            updateUiStateMataKuliah = repositoryMataKuliah.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMataKuliah()
        }

        // Fetch Dosen from repository
        viewModelScope.launch {
            val dosenListFromRepo = repositoryDosen.getAllDosen().first() // Fetch Dosen
            dosenList = dosenListFromRepo
            updateUiStateMataKuliah = updateUiStateMataKuliah.copy(dosenList = dosenList) // Update UI state with dosenList
        }
    }

//    fun updateStateMataKuliah(mataKuliahEvent: MataKuliahEvent) {
//        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(
//            // you can add additional state updates here if needed
//        )
//    }

    fun updateStateMataKuliah(mataKuliahEvent: MataKuliahEvent) {
        println("Update Event: $mataKuliahEvent")
        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(mataKuliahEvent = mataKuliahEvent)
    }

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

        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val  currentEvent = updateUiStateMataKuliah.mataKuliahEvent

        if (validateField()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    updateUiStateMataKuliah = updateUiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data Berhasil Diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorStateMataKuliah()
                    )
                    println("snackBarMessage diatur: ${updateUiStateMataKuliah.snackBarMessageMataKuliah}")
                }catch (e: Exception) {
                    updateUiStateMataKuliah =updateUiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data gaga; diupdate"
                    )
                }
            }
            fun  resetSnackBarMessage(){
                updateUiStateMataKuliah = updateUiStateMataKuliah.copy(snackBarMessageMataKuliah = null)
            }
        } else {
            updateUiStateMataKuliah = updateUiStateMataKuliah.copy(
                snackBarMessageMataKuliah = " Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUiStateMataKuliah = updateUiStateMataKuliah.copy(snackBarMessageMataKuliah = null)
    }
}

// Convert MataKuliah to UI State
fun MataKuliah.toUIStateMataKuliah(): MataKuliahUIState = MataKuliahUIState(
    mataKuliahEvent = this.toDetailUiEvent() // Convert MataKuliah to UI Event
)
