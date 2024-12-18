package com.example.pertemuan12.repository

import com.example.pertemuan12.dao.Dosendao
import com.example.pertemuan12.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoriDosen (
    private val dosendao: Dosendao
) : RepositoryDosen {
    override suspend fun insertDosen(dosen: Dosen) {
        dosendao.insertDosen(dosen)
    }
    //getAllDosen
    override fun getAllDosen(): Flow<List<Dosen>> {

        return dosendao.getAllDosen()
    }

    //getMhs
    override fun getDosen(nidn: String): Flow<Dosen> {
        return dosendao.getDosen(nidn)
    }

}