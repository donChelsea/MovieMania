package com.example.moviemania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviemania.ui.home.HomeViewModel
import com.example.moviemania.ui.home.ui.HomeScreen
import com.example.moviemania.ui.navigation.Actions
import com.example.moviemania.ui.navigation.Destinations
import com.example.moviemania.ui.navigation.Destinations.Home
import com.example.moviemania.ui.navigation.Destinations.MovieDetailArgs.MovieId
import com.example.moviemania.ui.navigation.Destinations.MovieDetails
import com.example.moviemania.ui.navigation.Destinations.WatchList
import com.example.moviemania.ui.theme.MovieManiaTheme
import com.example.moviemania.ui.watchlist.WatchListViewModel
import com.example.moviemania.ui.watchlist.ui.WatchListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private val watchListViewModel by viewModels<WatchListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieManiaTheme {
                val navController = rememberNavController()
                val actions = remember(navController) { Actions(navController) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.Home
                    ) {
                        composable(Home) {
                            HomeScreen(
                                viewModel = homeViewModel,
                                onWatchListClicked = actions.watchList,
                                onMovieDetailsClicked = actions.movieDetails,
                            )
                        }
                        composable(WatchList) {
                            WatchListScreen(
                                viewModel = watchListViewModel,
                                onBackClicked = actions.navigateBack
                            )
                        }
                        composable(
                            route = "$MovieDetails/{$MovieId}",
                            arguments = listOf(navArgument(MovieId) { type = NavType.IntType })
                        ) { backStackEntry ->
//                            TaskDetailScreen(
//                                viewModel = homeViewModel,
//                                taskId = backStackEntry.arguments?.getInt(MovieId) ?: 0,
//                                navigateBack = actions.navigateBack
//                            )
                        }
                    }
                }
            }
        }
    }
}