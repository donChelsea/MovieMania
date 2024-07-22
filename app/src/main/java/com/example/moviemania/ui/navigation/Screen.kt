package com.example.moviemania.ui.navigation

sealed class Screen(val route: String, val name: String) {
    object Home: Screen("home", "Home")
    object WatchList: Screen("watchlist", "Watchlist")
    object MovieDetails: Screen("movieDetails", "Details")

    object MovieDetailArgs {
        const val MovieId = "movieId"
    }

    fun withArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}