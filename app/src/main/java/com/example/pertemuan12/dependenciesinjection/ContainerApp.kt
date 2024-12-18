package com.example.pertemuan12.dependenciesinjection

import android.content.Context
import com.example.pertemuan12.database.ProdiTI
import com.example.pertemuan12.repository.LocalRepositoriDosen
import com.example.pertemuan12.repository.LocalRepositoriMataKuliah
import com.example.pertemuan12.repository.RepositoryDosen
import com.example.pertemuan12.repository.RepositoryMataKuliah

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
    val repositoryMataKuliah: RepositoryMataKuliah
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoriDosen(ProdiTI.getDatabase(context).dosenDao())
    }
    override val repositoryMataKuliah: RepositoryMataKuliah by lazy {
        LocalRepositoriMataKuliah(ProdiTI.getDatabase(context).mataKuliahDao())
    }
}