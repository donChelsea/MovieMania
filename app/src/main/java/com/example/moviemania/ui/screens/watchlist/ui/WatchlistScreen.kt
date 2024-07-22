package com.example.moviemania.ui.screens.watchlist.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.ui.composables.cards.ListItemCard
import com.example.moviemania.ui.composables.screens.ShowError
import com.example.moviemania.ui.composables.screens.ShowLoading
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.watchlist.ScreenData
import com.example.moviemania.ui.screens.watchlist.WatchlistUiAction
import com.example.moviemania.ui.screens.watchlist.WatchlistUiEvent
import com.example.moviemania.ui.screens.watchlist.WatchlistUiState
import com.example.moviemania.ui.screens.watchlist.WatchlistViewModel

@Composable
fun WatchlistScreen(
    navController: NavController,
) {
    val viewModel: WatchlistViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
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
        ScreenData.Initial -> {}
        ScreenData.Error -> ShowError()
        ScreenData.Loading -> ShowLoading()
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
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(movies) { movie ->
            ListItemCard(
                movie = movie,
                onClick = { onAction(WatchlistUiAction.OnMovieClicked(it)) }
            )
        }
    }
}
