package com.example.moviemania.ui.screens.home

import androidx.compose.runtime.Immutable
import com.example.moviemania.ui.model.MovieUiModel

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
    data object Initial : ScreenData()
    data object Loading : ScreenData()
    data object Error : ScreenData()

    @Immutable
    data class Data(
        val trending: List<MovieUiModel> = emptyList(),
        val nowPlaying: List<MovieUiModel> = emptyList(),
        val upcoming: List<MovieUiModel> = emptyList(),
    ) : ScreenData()
}