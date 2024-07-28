package com.example.moviemania.ui.screens.watch_later

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

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
    data class onDeleteMovie(val movie: Movie): WatchLaterUiAction()
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