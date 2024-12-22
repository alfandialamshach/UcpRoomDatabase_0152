package com.example.pertemuan12.ui.view.Dosen


import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.ui.view.MataKuliah.HomeUiState

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDosenViewModel (
    private  val repositoryDosen: RepositoryDosen
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiStateDosen> = repositoryDosen.getAllDosen()
        .filterNotNull()
        .map {
            HomeUiStateDosen(
                listDosen = it.toList(),
                isloadingDosen = false,

                )
        }
        .onStart {
            emit(HomeUiStateDosen(isloadingDosen = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateDosen(
                    isloadingDosen = false,
                    isError = true,
                    errorMessageDosen = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateDosen(
                isloadingDosen = true,
            )
        )
}
//untuk mengelola dan menyimpan state (status) dari UI (User Interface) di dalam aplikasi.
data class HomeUiStateDosen(
    val listDosen: List<Dosen> = listOf(),
    val isloadingDosen: Boolean = false,
    val isError: Boolean = false,
    val errorMessageDosen: String = ""
)