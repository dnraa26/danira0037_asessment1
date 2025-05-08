package com.danira0037.asessment1.navigation

import com.danira0037.asessment1.ui.screen.KEY_ID_DIARY

sealed class Screen(val route: String)  {
    data object Home : Screen("mainScreen")
    data object Add : Screen("addScreen")
    data object Edit : Screen("editScreen/{$KEY_ID_DIARY}"){
        fun withId(id : Long) = "editScreen/$id"
    }
}