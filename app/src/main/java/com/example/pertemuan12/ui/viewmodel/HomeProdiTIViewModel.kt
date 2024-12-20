package com.example.pertemuan12.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.repository.RepositoryMataKuliah

class HomeProdiTIViewModel(
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    // StateFlow for Dosen data
    val homeUiStateDosen: StateFlow<HomeUiStateDosen> = repositoryDosen.getAllDosen()
        .filterNotNull() // Ensure non-null data is emitted
        .map {
            HomeUiStateDosen(
                listDosen = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiStateDosen(isLoading = true)) // Emit loading state initially
            delay(500) // Optional: Delay for loading indicator (adjust as needed)
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
            initialValue = HomeUiStateDosen(isLoading = true) // Initial state is loading
        )

    // StateFlow for MataKuliah data
    val homeUiStateMataKuliah: StateFlow<HomeUiStateMataKuliah> = repositoryMataKuliah.getAllMataKuliah()
        .filterNotNull() // Ensure non-null data is emitted
        .map {
            HomeUiStateMataKuliah(
                listMataKuliah = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiStateMataKuliah(isLoading = true)) // Emit loading state initially
            delay(500) // Optional: Delay for loading indicator (adjust as needed)
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
            initialValue = HomeUiStateMataKuliah(isLoading = true) // Initial state is loading
        )
}

// UI State data classes for Dosen and Mata Kuliah
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
