package com.example.moviemania.ui.screens.watchlist

import com.example.moviemania.domain.models.Movie

data class WatchListUiState(
    val movies: List<Movie> = listOf(),
    val isLoading: Boolean = false,
)

sealed class WatchListUiEvent {
    object OnNavigateBack: WatchListUiEvent()
}

sealed class WatchListUiAction {
    object OnNavigateBack: WatchListUiAction()
}