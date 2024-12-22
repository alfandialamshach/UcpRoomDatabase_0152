package com.example.pertemuan12.ui.viewmodel


import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan12.ProdiTIApp
import com.example.pertemuan12.ui.view.Dosen.HomeDosenViewModel
import com.example.pertemuan12.ui.view.MataKuliah.HomeMataKuliahViewModel


object PenyediaViewModelProdiTI{

    val Factory = viewModelFactory {
        initializer {
            MataKuliahViewModel(
                ProdiTIApp().containerApp.repositoryMataKuliah,
                ProdiTIApp().containerApp.repositoryDosen
            )
        }

        //Dependency Injection
        initializer {
            DosenViewModel(
                ProdiTIApp().containerApp.repositoryDosen
            )
        }

        //Dependency Injection
        initializer {
            HomeMataKuliahViewModel(
                ProdiTIApp().containerApp.repositoryMataKuliah,
                ProdiTIApp().containerApp.repositoryDosen

            )
        }

        //Dependency Injection
        initializer {
            HomeDosenViewModel(
                ProdiTIApp().containerApp.repositoryDosen
            )
        }

        //Dependency Injection
        initializer {
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                ProdiTIApp().containerApp.repositoryMataKuliah
            )
        }

        //Dependency Injection
        initializer {
            UpdateMataKuliahViewModel(
                createSavedStateHandle(),
                ProdiTIApp().containerApp.repositoryMataKuliah,
                ProdiTIApp().containerApp.repositoryDosen
            )
        }
    }

}

fun CreationExtras.ProdiTIApp(): ProdiTIApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ProdiTIApp)