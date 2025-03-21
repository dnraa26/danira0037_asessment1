package com.danira0037.asessment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danira0037.asessment1.ui.screen.AddScreen
import com.danira0037.asessment1.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route){
            MainScreen(navController)
        }
        composable(Screen.Add.route){
            AddScreen(navController)
        }
    }
}