package com.example.moviemania.ui.screens.home

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

@Immutable
data class HomeUiState(
    val screenData: ScreenData = ScreenData.Initial
)

@Immutable
sealed class HomeUiEvent {
    @Immutable
    data class OnMovieClicked(val movieId: Int): HomeUiEvent()
}

@Immutable
sealed class HomeUiAction {
    @Immutable
    data class OnMovieClicked(val movieId: Int): HomeUiAction()
}

@Immutable
sealed class ScreenData {
    object Initial : ScreenData()
    object Loading : ScreenData()
    object Error : ScreenData()

    @Immutable
    data class Data(
        val trending: List<Movie> = emptyList(),
        val nowPlaying: List<Movie> = emptyList(),
        val upcoming: List<Movie> = emptyList(),
    ) : ScreenData()
}