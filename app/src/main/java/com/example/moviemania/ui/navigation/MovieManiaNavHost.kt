@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviemania.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviemania.R
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MovieId
import com.example.moviemania.ui.screens.details.ui.MovieDetailsScreen
import com.example.moviemania.ui.screens.home.ui.HomeScreen
import com.example.moviemania.ui.screens.watch_later.ui.WatchLaterScreen

@Composable
fun MovieManiaNavHost() {
    var title by remember { mutableStateOf("") }
    val canNavigateBack = (title != Screen.Home.name) && title.isNotEmpty()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            NavTopBar(
                title = title,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.popBackStack() },
                actions = {
                    if (title != Screen.WatchLater.name) {
                        IconButton(onClick = { navController.navigate(Screen.WatchLater.route) }) {
                            Icon(Icons.Outlined.List, stringResource(id = R.string.content_description_go_to_watch_list))
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(navController = navController)
                title = Screen.Home.name
            }
            composable(route = Screen.WatchLater.route) {
                WatchLaterScreen(navController = navController)
                title = Screen.WatchLater.name
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
                title = Screen.MovieDetails.name
            }
        }
    }
}