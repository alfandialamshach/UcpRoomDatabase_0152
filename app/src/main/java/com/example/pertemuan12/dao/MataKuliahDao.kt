package com.example.pertemuan12.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun  insertMataKuliah(mataKuliah: MataKuliah)

    //getAllMataKuliah
    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    //getMataKuliah
    @Query("SELECT * FROM mataKuliah WHERE kode = :kode")
    fun getDosen (kode: String) : Flow<Dosen>

    //deleteMatakuliah
    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    //UpdateMataKuliah
    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

}