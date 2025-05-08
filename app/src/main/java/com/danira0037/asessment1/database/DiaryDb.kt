package com.danira0037.asessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danira0037.asessment1.model.Diary
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Diary::class], version = 1, exportSchema = false)
abstract class DiaryDb : RoomDatabase(){

    abstract val dao: DiaryDao

    companion object{

        @Volatile
        private var INSTANCE : DiaryDb? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context : Context): DiaryDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryDb::class.java,
                        "diary.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}