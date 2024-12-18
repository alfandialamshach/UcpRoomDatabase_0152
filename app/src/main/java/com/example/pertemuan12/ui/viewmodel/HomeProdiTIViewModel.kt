package com.example.pertemuan12.ui.viewmodel

import androidx.compose.ui.input.key.Key.Companion.Home

import androidx.lifecycle.viewModelScope


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.ViewModel
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.repository.RepositoryMataKuliah


class HomeProTIViewModel(
    private val repositoryDosen: RepositoryDosen,
    private val repositoryMataKuliah: RepositoryMataKuliah
) : ViewModel() {

    val homeUiStateDosen: StateFlow<HomeUiStateDosen> = repositoryDosen.getAllDosen()
        .filterNotNull()
        .map {
            HomeUiStateDosen(
                listDosen = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiStateDosen(isLoading = true))
            delay(900)
        }
        .catch { throwable ->
            emit(
                HomeUiStateDosen(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateDosen(isLoading = true)
        )

    val homeUiStateMataKuliah: StateFlow<HomeUiStateMataKuliah> = repositoryMataKuliah.getAllMataKuliah()
        .filterNotNull()
        .map {
            HomeUiStateMataKuliah(
                listMataKuliah = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiStateMataKuliah(isLoading = true))
            delay(900)
        }
        .catch { throwable ->
            emit(
                HomeUiStateMataKuliah(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateMataKuliah(isLoading = true)
        )
}

data class HomeUiStateDosen(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

data class HomeUiStateMataKuliah(
    val listMataKuliah: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

