package com.example.pertemuan12

import android.app.Application
import com.example.pertemuan12.dependenciesinjection.ContainerApp

class ProdiTIApp : Application() {
    lateinit var containerApp: ContainerApp // fungsinya untuk menyimpan dependensi

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) // Menginisialisasi ContainerApp dengan context aplikasi
    }
}
