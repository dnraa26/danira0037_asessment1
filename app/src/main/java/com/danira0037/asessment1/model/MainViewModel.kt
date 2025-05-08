package com.danira0037.asessment1.model

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val data = listOf(
        Diary(
            id = 1,
            judul = "Hari Senin",
            isi = "Apapun itu",
            tanggal = "01/01/01",
            mood = "Senang"
        ),Diary(
            id = 2,
            judul = "Hari Selasa",
            isi = "Apapun itu Selasa",
            tanggal = "01/01/02",
            mood = "Senang"
        ),Diary(
            id = 3,
            judul = "Hari Rabu",
            isi = "Apapun itu Rabu",
            tanggal = "01/01/03",
            mood = "Senang"
        )
    )



}