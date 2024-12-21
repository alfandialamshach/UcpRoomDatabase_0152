package com.example.pertemuan12.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pertemuan12.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface Dosendao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    //getAllMahasiswa
    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>


}