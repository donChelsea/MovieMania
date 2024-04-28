@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviemania.ui.screens.watchlist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviemania.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.ui.composables.MovieManiaWatchListCard
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.empty.ErrorScreen
import com.example.moviemania.ui.screens.empty.LoadingScreen
import com.example.moviemania.ui.screens.watchlist.ScreenData
import com.example.moviemania.ui.screens.watchlist.WatchlistUiAction
import com.example.moviemania.ui.screens.watchlist.WatchlistUiEvent
import com.example.moviemania.ui.screens.watchlist.WatchlistUiState
import com.example.moviemania.ui.screens.watchlist.WatchlistViewModel

@Composable
fun WatchlistScreen(
    navController: NavController
) {
    val viewModel: WatchlistViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                WatchlistUiEvent.OnNavigateBack -> navController.popBackStack()
                is WatchlistUiEvent.OnMovieClicked -> navController.navigate(Screen.MovieDetails.withArgs(event.movieId))
            }
        }
    }

    WatchlistLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}

@Composable
fun WatchlistLayout(
    state: WatchlistUiState,
    onAction: (WatchlistUiAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Empty -> {}
        ScreenData.Error -> ErrorScreen()
        ScreenData.Loading -> LoadingScreen()
        ScreenData.Offline -> {}
        is ScreenData.Data -> WatchlistContent(
            movies = state.screenData.movies,
            onAction = onAction
        )
    }
}


@Composable
fun WatchlistContent(
    movies: List<Movie>,
    onAction: (WatchlistUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.watch_list))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                navigationIcon = {
                    IconButton(onClick = { onAction(WatchlistUiAction.OnNavigateBack) }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }, content = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(movies) { movie ->
                    MovieManiaWatchListCard(
                        movie = movie,
                        onClick = { onAction(WatchlistUiAction.OnMovieClicked(it)) }
                    )
                }
            }
        }
    )
}