package com.example.pertemuan12.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pertemuan12.dao.Dosendao
import com.example.pertemuan12.dao.MataKuliahDao
import com.example.pertemuan12.entity.Dosen
import com.example.pertemuan12.entity.MataKuliah

@Database(
    entities = [Dosen::class, MataKuliah::class], // Gunakan array untuk daftar entity
    version = 1,
    exportSchema = false
)
abstract class ProdiTI : RoomDatabase() {

    // Mendefinisikan fungsi untuk mengakses data Dosen
    abstract fun dosenDao(): Dosendao

    // Mendefinisikan fungsi untuk mengakses data MataKuliah
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object {
        @Volatile // Memastikan nilai variabel Instance selalu sama di seluruh thread
        private var INSTANCE: ProdiTI? = null

        fun getDatabase(context: Context): ProdiTI {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProdiTI::class.java, // Class database
                    "KrsDatabase" // Nama database
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
