package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryMataKuliah
import com.example.pertemuan12.ui.Navigation.DestinasiUpdateMataKuliah

import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah
) : ViewModel() {

    var updateUiState by mutableStateOf(MataKuliahUIState())
        private set
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMataKuliah.KODE])

    init {
        viewModelScope.launch{
            updateUiState = repositoryMataKuliah.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMataKuliah()
        }
    }
    fun updateState (mataKuliahEvent: MataKuliahEvent) {
        updateUiState = updateUiState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }

    fun validateField(): Boolean {
        val event = updateUiState.mataKuliahEvent
        val errorState =FormErrorStateMataKuliah (
            kode = if (event.kode.isNotEmpty()) null else "NIM Tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama Tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "Jenis Kelamin Tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Alamat Tidak boleh kosong",
            jenisMataKulih = if (event.jenisMataKulih.isNotEmpty()) null else "Kelas Tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Angkatan Tidak boleh kosong"
        )

        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val  currentEvent = updateUiState.mataKuliahEvent

        if (validateField()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessageMataKuliah = "Data Berhasil Diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorStateMataKuliah()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessageMataKuliah}")
                }catch (e: Exception) {
                    updateUiState =updateUiState.copy(
                        snackBarMessageMataKuliah = "Data gaga; diupdate"
                    )
                }
            }
            fun  resetSnackBarMessage(){
                updateUiState = updateUiState.copy(snackBarMessageMataKuliah = null)
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessageMataKuliah = " Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessageMataKuliah = null)
    }

}
fun MataKuliah.toUIStateMataKuliah() : MataKuliahUIState = MataKuliahUIState(
    mataKuliahEvent = this.toDetailUiEvent()  ,
)