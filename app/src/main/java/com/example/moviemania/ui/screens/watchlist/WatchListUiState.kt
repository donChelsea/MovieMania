package com.example.moviemania.ui.screens.watchlist

import com.example.moviemania.domain.models.Movie

data class WatchListUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed class WatchListUiEvent {
    object OnNavigateBack: WatchListUiEvent()
    data class OnMovieClicked(val movieId: Int): WatchListUiEvent()
}

sealed class WatchListUiAction {
    object OnNavigateBack: WatchListUiAction()
    data class OnMovieClicked(val movieId: Int): WatchListUiAction()
}