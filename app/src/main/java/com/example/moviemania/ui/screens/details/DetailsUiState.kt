package com.example.moviemania.ui.screens.details

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.models.Video
import com.example.moviemania.ui.composables.bookmark.BookmarkState

@Immutable
data class DetailsUiState(
    val screenData: ScreenData = ScreenData.Empty,
    val currentlyPlayingIndex: Int = 0,
    val lastPlayedPosition: Int = 0,
)

@Immutable
sealed class DetailsUiEvent {}

@Immutable
sealed class DetailsUiAction {
    @Immutable
    data class OnUpdateWatchLater(val movie: Movie, val bookmarked: Boolean) : DetailsUiAction()
}

@Immutable
sealed class ScreenData {
    object Empty : ScreenData()
    object Loading : ScreenData()
    object Error : ScreenData()

    @Immutable
    data class Data(
        val movie: Movie? = null,
        val trailer: Video? = null,
        val bookmarked: BookmarkState = BookmarkState.NotBookmarked,
    ) : ScreenData()
}