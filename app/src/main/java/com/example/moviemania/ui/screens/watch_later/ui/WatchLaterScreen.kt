package com.example.moviemania.ui.screens.watch_later.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.moviemania.ui.model.MovieUiModel
import com.example.moviemania.ui.custom.cards.ListCard
import com.example.moviemania.ui.custom.states.ShowError
import com.example.moviemania.ui.custom.states.ShowLoading
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.watch_later.ScreenData
import com.example.moviemania.ui.screens.watch_later.WatchLaterUiAction
import com.example.moviemania.ui.screens.watch_later.WatchLaterUiEvent
import com.example.moviemania.ui.screens.watch_later.WatchLaterUiState
import com.example.moviemania.ui.screens.watch_later.WatchLaterViewModel

@Composable
fun WatchLaterScreen(
    navController: NavController,
) {
    val viewModel: WatchLaterViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.events.collect { event ->
            when (event) {
                is WatchLaterUiEvent.OnMovieClicked -> navController.navigate(Screen.MovieDetails.withArgs(event.movieId))
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
    state: WatchLaterUiState,
    onAction: (WatchLaterUiAction) -> Unit,
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
    movies: List<MovieUiModel>,
    onAction: (WatchLaterUiAction) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(movies) { movie ->
            ListCard(
                movieUiModel = movie,
                onClick = { onAction(WatchLaterUiAction.OnMovieClicked(it)) },
                onDelete = { onAction(WatchLaterUiAction.OnDeleteMovie(it)) }
            )
        }
    }
}
