package com.example.pertemuan12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.repository.RepositoryMataKuliah
import kotlinx.coroutines.launch

class MataKuliahViewModel (private  val repositoryMataKuliah: RepositoryMataKuliah) : ViewModel(){

    var uiStateMataKuliah by mutableStateOf(MataKuliahUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateStatematakuliah(mataKuliahEvent: MataKuliahEvent){
        uiStateMataKuliah = uiStateMataKuliah.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }
    //validasi data input pengguna
    private fun validateFields(): Boolean{
        val eventMataKuliah = uiStateMataKuliah.mataKuliahEvent
        val errorStateMatakuliah = FormErrorStateMataKuliah(
            kode = if (eventMataKuliah.kode.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (eventMataKuliah.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (eventMataKuliah.sks.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            semester = if (eventMataKuliah.semester.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jenisMataKulih = if (eventMataKuliah.jenisMataKulih.isNotEmpty()) null else "Alamat tidak boleh kosong",
            dosenPengampu = if (eventMataKuliah.dosenPengampu.isNotEmpty()) null else "Alamat tidak boleh kosong"
        )
        uiStateMataKuliah = uiStateMataKuliah.copy(isEntryValid = errorStateMatakuliah)
        return errorStateMatakuliah.isValid()
    }

    //Menyimpan data ke repository
    fun saveDataMataKuliah() {
        val currentEventMataKuliah = uiStateMataKuliah.mataKuliahEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMataKuliah(currentEventMataKuliah.toMataKuliahEntity())
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = "Data Berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(), // Reset Input form
                        isEntryValid = FormErrorStateMataKuliah() // Reset Error State
                    )
                } catch (e: Exception) {
                    uiStateMataKuliah =uiStateMataKuliah.copy(
                        snackBarMessageMataKuliah = " Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateMataKuliah = uiStateMataKuliah.copy(
                snackBarMessageMataKuliah = "Input tidak valid. Perikasa kembali dta anda"
            )
        }
    }

    //Reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessageMataKuliah() {
        uiStateMataKuliah = uiStateMataKuliah.copy(snackBarMessageMataKuliah = null)
    }
}

data class MataKuliahUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorStateMataKuliah = FormErrorStateMataKuliah(),
    val snackBarMessageMataKuliah:String?= null,
)

data class FormErrorStateMataKuliah(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenisMataKulih: String? = null,
    val dosenPengampu: String? = null


){
    fun isValid(): Boolean {
        return kode == null && nama == null && sks == null &&
                semester == null && jenisMataKulih == null && dosenPengampu == null
    }
}
//data class variabel yang menyimpan data input form
data class MataKuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenisMataKulih: String = "",
    val dosenPengampu: String = ""
)

//menyimpan input form ke dalam entity
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenisMataKulih = jenisMataKulih,
    dosenPengampu = dosenPengampu
)