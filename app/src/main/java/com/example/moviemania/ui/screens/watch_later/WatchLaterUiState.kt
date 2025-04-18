package com.example.moviemania.ui.screens.watch_later

import androidx.compose.runtime.Immutable
import com.example.moviemania.ui.model.MovieUiModel

@Immutable
data class WatchLaterUiState(
    val screenData: ScreenData = ScreenData.Initial
)

@Immutable
sealed class WatchLaterUiEvent {
    @Immutable
    data class OnMovieClicked(val movieId: Int): WatchLaterUiEvent()
}

@Immutable
sealed class WatchLaterUiAction {
    @Immutable
    data class OnMovieClicked(val movieId: Int): WatchLaterUiAction()
    @Immutable
    data class OnDeleteMovie(val movieUiModel: MovieUiModel): WatchLaterUiAction()
}

@Immutable
sealed class ScreenData {
    data object Initial : ScreenData()
    data object Offline : ScreenData()
    data object Loading : ScreenData()

    @Immutable
    data class Error(val message: String) : ScreenData()

    @Immutable
    data class Data(
        val movies: List<MovieUiModel> = emptyList(),
    ) : ScreenData()
}