package com.example.pertemuan12.repository

import com.example.pertemuan12.dao.Dosendao
import com.example.pertemuan12.dao.MataKuliahDao
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import kotlinx.coroutines.flow.Flow


class LocalRepositoriMataKuliah(
    private val mataKuliahDao: MataKuliahDao
) : RepositoryMataKuliah {
    override suspend fun insertMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }
    //getAllMataKuliah
    override fun getAllMataKuliah(): Flow<List<MataKuliah>> {

        return mataKuliahDao.getAllMataKuliah()
    }

    //getMhs
    override fun getMataKuliah(kode: String): Flow<MataKuliah>{
        return mataKuliahDao.getMataKuliah(kode)
    }
    override suspend fun deleteMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMataKuliah(mataKuliah)
    }

    //updateMhs
    override suspend fun updateMataKuliah(mataKuliah: MataKuliah){
        mataKuliahDao.updateMataKuliah(mataKuliah)
    }

}