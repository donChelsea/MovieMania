package com.example.moviemania.ui.home

import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie

data class HomeUiState(
    val trending: List<Movie> = listOf(),
    val nowPlaying: List<Movie> = listOf(),
    val upcoming: List<Movie> = listOf(),
    val genres: List<Genre> = listOf(),
    val isLoading: Boolean = false,
)

sealed class HomeUiEvent {}

sealed class HomeUiAction {}