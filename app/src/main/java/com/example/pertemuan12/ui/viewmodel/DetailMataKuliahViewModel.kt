package com.example.pertemuan12.ui.viewmodel

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryMataKuliah
import com.example.pertemuan12.ui.Navigation.DestinasiDetailMatakuliah

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,

    ) : ViewModel() {
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMatakuliah.KODE])

    val detailUiState: StateFlow<DetailUiState> = repositoryMataKuliah.getMataKuliah(_kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",

                    )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

    fun deleteMhs() {
        detailUiState.value.detailUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch {
                repositoryMataKuliah.deleteMataKuliah(it)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpety: Boolean
        get() = detailUiEvent == MataKuliahEvent()

    val isUiEventNotEmpety: Boolean
        get() =detailUiEvent != MataKuliahEvent()
}

// Data class untuk menampung data yang akan ditampilkan di UI

//memindahkan data dari entity ke ui
fun MataKuliah.toDetailUiEvent(): MataKuliahEvent {
    return MataKuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenisMataKulih = jenisMataKulih,
        dosenPengampu = dosenPengampu
    )
}