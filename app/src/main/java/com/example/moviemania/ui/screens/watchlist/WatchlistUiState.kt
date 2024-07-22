package com.example.moviemania.ui.screens.watchlist

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

@Immutable
data class WatchlistUiState(
    val screenData: ScreenData = ScreenData.Initial
)

@Immutable
sealed class WatchlistUiEvent {
    @Immutable
    data class OnMovieClicked(val movieId: Int): WatchlistUiEvent()
}

@Immutable
sealed class WatchlistUiAction {
    @Immutable
    data class OnMovieClicked(val movieId: Int): WatchlistUiAction()
}

@Immutable
sealed class ScreenData {
    object Initial : ScreenData()
    object Loading : ScreenData()
    object Error : ScreenData()

    @Immutable
    data class Data(
        val movies: List<Movie> = emptyList(),
    ) : ScreenData()
}