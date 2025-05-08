package com.danira0037.asessment1.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danira0037.asessment1.database.DiaryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: DiaryDao) : ViewModel() {

    fun insert(judul : String, isi : String, tanggal : String, mood : String){
        val diary = Diary(
            judul = judul,
            isi = isi,
            tanggal = tanggal,
            mood = mood
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(diary)
        }
    }

    suspend fun getDiary(id: Long): Diary? {
        return dao.getDiaryById(id)
    }

    fun update(id : Long, judul : String, isi : String, tanggal : String, mood : String){
        val diary = Diary(
            id = id,
            judul = judul,
            isi = isi,
            tanggal = tanggal,
            mood = mood
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(diary)
        }
    }
}