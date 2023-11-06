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
import com.example.moviemania.ui.screens.watchlist.WatchListViewModel
import com.example.moviemania.ui.screens.watchlist.ui.WatchListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
        composable(route = Screen.WatchList.route) {
            val viewModel = hiltViewModel<WatchListViewModel>()
            WatchListScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = Screen.MovieDetails.route + "/{$MovieId}",
            arguments = listOf(
                navArgument(MovieId) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<MovieDetailsViewModel>()
            MovieDetailsScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}