package com.example.pertemuan12.ui.viewmodel


import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan12.ProdiTIApp


object PenyediaViewModelProdiTI{

    val Factory = viewModelFactory {
        initializer {
            MataKuliahViewModel(
                ProdiTIApp().containerApp.repositoryMataKuliah,
                ProdiTIApp().containerApp.repositoryDosen // Pastikan repositoryDosen juga ada
            )
        }

//        initializer {
//            HomeProTIViewModel(
//                ProdiTIApp().containerApp.repositoryMataKuliah
//            )
//        }

        initializer {
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                ProdiTIApp().containerApp.repositoryMataKuliah
            )
        }

        initializer {
            UpdateMataKuliahViewModel(
                createSavedStateHandle(),
                ProdiTIApp().containerApp.repositoryMataKuliah
            )
        }
    }

}

fun CreationExtras.ProdiTIApp(): ProdiTIApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProdiTIApp)