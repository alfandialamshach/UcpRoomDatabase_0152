package com.example.pertemuan12.repository

import com.example.pertemuan12.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {

    suspend fun insertDosen(dosen: Dosen)

    //getAllDosen
    fun getAllDosen(): Flow<List<Dosen>>

    //getDosen
    fun getDosen(nim: String) : Flow<Dosen>


}