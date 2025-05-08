package com.danira0037.asessment1.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danira0037.asessment1.database.DiaryDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao : DiaryDao) : ViewModel() {
    val data : StateFlow<List<Diary>> = dao.getCatatan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )



}