package com.danira0037.asessment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danira0037.asessment1.ui.screen.AddScreen
import com.danira0037.asessment1.ui.screen.KEY_ID_DIARY
import com.danira0037.asessment1.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ){
            MainScreen(navController)
        }
        composable(
            route = Screen.Add.route
        ){
            AddScreen(navController)
        }
        composable(
            route = Screen.Edit.route,
            arguments = listOf(
                navArgument(KEY_ID_DIARY){
                    type = NavType.LongType
                }
            )
        ){  navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_DIARY)
            AddScreen(navController, id)
        }
    }
}