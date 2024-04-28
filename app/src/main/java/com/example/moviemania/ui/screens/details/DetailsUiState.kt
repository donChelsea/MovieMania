package com.example.moviemania.ui.screens.details

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie

@Immutable
data class DetailsUiState(
    val screenData: ScreenData = ScreenData.Empty
)

@Immutable
sealed class DetailsUiEvent {
    object OnNavigateBack : DetailsUiEvent()
}

@Immutable
sealed class DetailsUiAction {
    object OnNavigateBack : DetailsUiAction()

    @Immutable
    data class OnSaveToWatchlist(val movie: Movie) : DetailsUiAction()
}

@Immutable
sealed class ScreenData {
    object Empty : ScreenData()
    object Loading : ScreenData()
    object Error : ScreenData()
    object Offline : ScreenData()

    @Immutable
    data class Data(
        val movie: Movie? = null,
        val isSaved: Boolean = false
    ) : ScreenData()
}