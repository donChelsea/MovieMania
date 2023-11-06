package com.example.moviemania.ui.navigation

import androidx.navigation.NavHostController
import com.example.moviemania.ui.navigation.Destinations.MovieDetailArgs.MovieId
import com.example.moviemania.ui.navigation.Destinations.MovieDetails
import com.example.moviemania.ui.navigation.Destinations.WatchList


object Destinations {
    const val Home = "home"
    const val WatchList = "watchList"
    const val MovieDetails = "movieDetails"

    object MovieDetailArgs {
        const val MovieId = "movieId"
    }
}

class Actions(navController: NavHostController) {
    val watchList: () -> Unit = {
        navController.navigate(WatchList)
    }
    val movieDetails: () -> Unit = {
        navController.navigate("$MovieDetails/$MovieId")
    }
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}