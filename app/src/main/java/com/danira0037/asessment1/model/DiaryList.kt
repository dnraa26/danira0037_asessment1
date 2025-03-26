package com.danira0037.asessment1.model

object DiaryList {
    private val _diaryList = mutableListOf<Diary>()

    val diaryList: List<Diary> get() = _diaryList
    var tempDiary: Diary = Diary("", "", "00/00/0000", "Mood")

    fun addToDiary(diary : Diary){
        _diaryList.add(diary)

    }


}