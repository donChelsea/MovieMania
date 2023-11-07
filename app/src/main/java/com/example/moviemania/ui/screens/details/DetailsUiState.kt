package com.example.moviemania.ui.screens.details

import com.example.moviemania.domain.models.Movie

data class DetailsUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed class DetailsUiEvent {
    object OnNavigateBack: DetailsUiEvent()
}

sealed class DetailsUiAction {
    object OnNavigateBack: DetailsUiAction()
}