package com.example.moviemania.ui.home

import com.example.moviemania.domain.models.Movie

data class HomeUiState(
    val movies: List<Movie> = listOf(),
    val isLoading: Boolean = false,
)

sealed class HomeUiEvent {}

sealed class HomeUiAction {}