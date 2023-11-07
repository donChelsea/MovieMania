package com.example.moviemania.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object WatchList: Screen("watchList")
    object MovieDetails: Screen("movieDetails")

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