package com.example.moviemania.ui.navigation

sealed class Screen(val route: String, val name: String) {
    data object Home: Screen("home", "Home")
    data object WatchLater: Screen("watch_later", "Watch Later")
    data object MovieDetails: Screen("details", "Details")

    object MovieDetailArgs {
        const val MOVIE_ID = "movie_id"
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