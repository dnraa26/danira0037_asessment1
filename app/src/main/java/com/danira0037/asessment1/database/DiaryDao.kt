package com.danira0037.asessment1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.danira0037.asessment1.model.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Insert
    suspend fun insert(diary: Diary)

    @Update
    suspend fun update(diary: Diary)

    @Query("SELECT * FROM diary ORDER BY tanggal DESC")
    fun getCatatan(): Flow<List<Diary>>

    @Query("SELECT * FROM diary WHERE id = :id")
    suspend fun getDiaryById(id: Long): Diary?

}