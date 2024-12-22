package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.repository.RepositoryDosen
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DosenViewModel (private  val repositoryDosen:RepositoryDosen) : ViewModel(){

    // Variabel UI State untuk Dosen
    var uiStateDosen by mutableStateOf(DosenUIState())
        private set
    // Memperbarui state berdasarkan input pengguna
    fun updateStateDosen(dosenEvent: DosenEvent){
        uiStateDosen = uiStateDosen.copy(
            dosenEvent = dosenEvent,
        )
    }


    //validasi data input pengguna harus tidak boleh kosong
     fun validateFieldsDosen(): Boolean{
        val event = uiStateDosen.dosenEvent
        val errorState = FormErrorStateDosen(
            nidn = if (event.nidn.isNotEmpty()) null else "Nama tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "NIDN tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
        )
        // Update state untuk error message
        uiStateDosen = uiStateDosen.copy(isEntryValid = errorState)
        // Pastikan form valid jika tidak ada pesan error
        return errorState.isValidDosen()
    }

    //Menyimpan data ke repository Dosen
    fun saveData() {
        val currentEventDosen = uiStateDosen.dosenEvent
        // Jika validasi sukses, simpan data ke repository
        if (validateFieldsDosen()){
            viewModelScope.launch {
                try {
                    // Menyimpan data ke repository
                    repositoryDosen.insertDosen(currentEventDosen.toDosenEntity())
                    // Jika penyimpanan berhasil, reset form dan tampilkan pesan sukses
                    uiStateDosen = uiStateDosen.copy(
                        snackBarMessage = "Data Berhasil disimpan", // Pesan berhasil
                        dosenEvent = DosenEvent(), // Reset Input form
                        isEntryValid = FormErrorStateDosen() // Reset Error State
                    )
                } catch (e: Exception) {
                    uiStateDosen =uiStateDosen.copy(
                        snackBarMessage = " Data gagal disimpan"
                    )
                }
            }
            //untuk memperbarui state (keadaan) UI ketika input pengguna tidak valid.
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
//untuk merepresentasikan state (keadaan) dari UI yang berhubungan dengan entitas dosen.
data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorStateDosen = FormErrorStateDosen(),
    val snackBarMessage:String?= null,
)

//untuk merepresentasikan status kesalahan (error state) pada sebuah formulir
data class FormErrorStateDosen(
    val nidn: String? = null,
    val nama: String? = null,
    val alamat: String? = null,
    val jenisKelamin: String? = null

){  //untuk memvalidasi apakah data dosen dianggap valid atau tidak.
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
    jenisKelamin = jenisKelamin,
)
