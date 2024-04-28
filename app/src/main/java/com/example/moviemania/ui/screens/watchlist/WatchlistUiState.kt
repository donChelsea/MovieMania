package com.example.moviemania.ui.screens.watchlist

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

@Immutable
data class WatchlistUiState(
    val screenData: ScreenData = ScreenData.Empty
)

@Immutable
sealed class WatchlistUiEvent {
    object OnNavigateBack: WatchlistUiEvent()

    @Immutable
    data class OnMovieClicked(val movieId: Int): WatchlistUiEvent()
}

@Immutable
sealed class WatchlistUiAction {
    object OnNavigateBack: WatchlistUiAction()

    @Immutable
    data class OnMovieClicked(val movieId: Int): WatchlistUiAction()
}

@Immutable
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