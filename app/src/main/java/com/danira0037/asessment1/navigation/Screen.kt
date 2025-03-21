package com.danira0037.asessment1.navigation

sealed class Screen(val route: String)  {
    data object Home : Screen("mainScreen")
    data object Add : Screen("addScreen")
}