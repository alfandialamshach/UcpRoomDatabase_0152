package com.example.pertemuan12.repository

import com.example.pertemuan12.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMataKuliah {

    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    //getAllMhs
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    //getMhs
    fun getMataKuliah(nim: String) : Flow<MataKuliah>

    //deleteMhs
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    //updateMhs
    suspend fun updateMataKuliah (mataKuliah: MataKuliah)
}