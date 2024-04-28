package com.example.moviemania.ui.screens.watchlist

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

data class WatchlistUiState(
    val screenData: ScreenData = ScreenData.Empty
)

sealed class WatchlistUiEvent {
    object OnNavigateBack: WatchlistUiEvent()
    data class OnMovieClicked(val movieId: Int): WatchlistUiEvent()
}

sealed class WatchlistUiAction {
    object OnNavigateBack: WatchlistUiAction()
    data class OnMovieClicked(val movieId: Int): WatchlistUiAction()
}

sealed class ScreenData {
    object Empty : ScreenData()
    object Loading : ScreenData()
    object Error : ScreenData()
    object Offline : ScreenData()

    @Immutable
    data class Data(
        val movies: List<Movie> = emptyList(),
    ) : ScreenData()
}