package com.example.pertemuan12.dependenciesinjection

import android.content.Context
import com.example.pertemuan12.database.ProdiTI
import com.example.pertemuan12.repository.LocalRepositoriDosen
import com.example.pertemuan12.repository.RepositoryDosen

interface InterfaceContainerApp {
    val repositoryDosen: RepositoryDosen
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoriDosen(ProdiTI.getDatabase(context).dosenDao())
    }
}