package com.example.moviemania.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MovieId
import com.example.moviemania.ui.screens.details.MovieDetailsViewModel
import com.example.moviemania.ui.screens.details.ui.MovieDetailsScreen
import com.example.moviemania.ui.screens.home.HomeViewModel
import com.example.moviemania.ui.screens.home.ui.HomeScreen
import com.example.moviemania.ui.screens.watchlist.WatchlistViewModel
import com.example.moviemania.ui.screens.watchlist.ui.WatchlistScreen

@Composable
fun MovieManiaNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.WatchList.route) {
            WatchlistScreen(navController = navController)
        }
        composable(
            route = Screen.MovieDetails.route + "/{$MovieId}",
            arguments = listOf(
                navArgument(MovieId) {
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetailsScreen(navController = navController)
        }
    }
}