package com.danira0037.asessment1.model

object DiaryList {
    private val _diaryList = mutableListOf<Diary>()

    val diaryList: List<Diary> get() = _diaryList

    fun addToDiary(diary : Diary){
        _diaryList.add(diary)

    }
}