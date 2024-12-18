package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.repository.RepositoryDosen
import kotlinx.coroutines.launch

class MahasiswaViewModel (private  val repositoryDosen:RepositoryDosen) : ViewModel(){

    var uiState by mutableStateOf(DosenUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent,
        )
    }
    //validasi data input pengguna
    private fun validateFields(): Boolean{
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        dosenEvent = DosenEvent(), // Reset Input form
                        isEntryValid = FormErrorState() // Reset Error State
                    )
                } catch (e: Exception) {
                    uiState =uiState.copy(
                        snackBarMessage = " Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Perikasa kembali dta anda"
            )
        }
    }

    //Reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage:String?= null,
)

data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val alamat: String? = null,
    val jenisKelamin: String? = null

){
    fun isValid(): Boolean {
        return nidn == null && nama == null && jenisKelamin == null &&
                alamat == null
    }
}
//data class variabel yang menyimpan data input form
data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",

)

//menyimpan input form ke dalam entity
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
)