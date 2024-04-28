package com.example.moviemania.ui.screens.home

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

data class HomeUiState(
    val screenData: ScreenData = ScreenData.Empty
)

sealed class HomeUiEvent {
    object GoToWatchlist: HomeUiEvent()
    data class OnMovieClicked(val movieId: Int): HomeUiEvent()
}

sealed class HomeUiAction {
    object GoToWatchlist: HomeUiAction()
    data class OnMovieClicked(val movieId: Int): HomeUiAction()
}

sealed class ScreenData {
    object Empty : ScreenData()
    object Loading : ScreenData()
    object Error : ScreenData()
    object Offline : ScreenData()

    @Immutable
    data class Data(
        val trending: List<Movie> = emptyList(),
        val nowPlaying: List<Movie> = emptyList(),
        val upcoming: List<Movie> = emptyList(),
    ) : ScreenData()
}