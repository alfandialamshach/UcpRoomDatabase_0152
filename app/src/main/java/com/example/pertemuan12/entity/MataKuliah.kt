package com.example.pertemuan12.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mataKuliah")
data class MataKuliah(
    @PrimaryKey
    val kode: String,
    val nama: String,
    val sks: String,
    val semester: String,
    val jenisMataKulih: String,
    val dosenPengampu: String
)
