package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.repository.RepositoryDosen
import kotlinx.coroutines.launch

class DosenViewModel (private  val repositoryDosen:RepositoryDosen) : ViewModel(){

    var uiStateDosen by mutableStateOf(DosenUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateStateDosen(dosenEvent: DosenEvent){
        uiStateDosen = uiStateDosen.copy(
            dosenEvent = dosenEvent,
        )
    }
    //validasi data input pengguna
    private fun validateFieldsDosen(): Boolean{
        val event = uiStateDosen.dosenEvent
        val errorState = FormErrorStateDosen(
            nidn = if (event.nidn.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong"
        )
        uiStateDosen = uiStateDosen.copy(isEntryValid = errorState)
        return errorState.isValidDosen()
    }

    //Menyimpan data ke repository
    fun saveData() {
        val currentEventDosen = uiStateDosen.dosenEvent
        if (validateFieldsDosen()){
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEventDosen.toDosenEntity())
                    uiStateDosen = uiStateDosen.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        dosenEvent = DosenEvent(), // Reset Input form
                        isEntryValid = FormErrorStateDosen() // Reset Error State
                    )
                } catch (e: Exception) {
                    uiStateDosen =uiStateDosen.copy(
                        snackBarMessage = " Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateDosen = uiStateDosen.copy(
                snackBarMessage = "Input tidak valid. Perikasa kembali dta anda"
            )
        }
    }

    //Reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessageDosen() {
        uiStateDosen = uiStateDosen.copy(snackBarMessage = null)
    }
}

data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorStateDosen = FormErrorStateDosen(),
    val snackBarMessage:String?= null,
)

data class FormErrorStateDosen(
    val nidn: String? = null,
    val nama: String? = null,
    val alamat: String? = null,
    val jenisKelamin: String? = null

){
    fun isValidDosen(): Boolean {
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