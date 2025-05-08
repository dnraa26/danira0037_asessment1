package com.danira0037.asessment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val judul : String,
    val isi : String,
    val tanggal : String,
    val mood : String
)
