package com.example.moviemania.ui.screens.home

import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie

data class HomeUiState(
    val trending: List<Movie> = emptyList(),
    val nowPlaying: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed class HomeUiEvent {
    object OnWatchListClicked: HomeUiEvent()
    data class OnMovieClicked(val movieId: Int): HomeUiEvent()
}

sealed class HomeUiAction {
    object OnWatchListClicked: HomeUiAction()
    data class OnMovieClicked(val movieId: Int): HomeUiAction()
}