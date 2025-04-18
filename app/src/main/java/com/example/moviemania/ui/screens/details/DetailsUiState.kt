package com.example.moviemania.ui.screens.details

import androidx.compose.runtime.Immutable
import com.example.moviemania.domain.model.VideoUiModel
import com.example.moviemania.ui.custom.bookmark.BookmarkState
import com.example.moviemania.ui.model.MovieUiModel

@Immutable
data class DetailsUiState(
    val screenData: ScreenData = ScreenData.Empty,
    val currentlyPlayingIndex: Int = 0,
    val lastPlayedPosition: Int = 0,
)

@Immutable
sealed class DetailsUiEvent

@Immutable
sealed class DetailsUiAction {
    @Immutable
    data class OnUpdateWatchLater(
        val movieUiModel: MovieUiModel,
        val bookmarked: Boolean
    ) : DetailsUiAction()
}

@Immutable
sealed class ScreenData {
    data object Empty : ScreenData()
    data object Loading : ScreenData()
    data object Error : ScreenData()

    @Immutable
    data class Data(
        val movieUiModel: MovieUiModel? = null,
        val trailer: VideoUiModel? = null,
        val bookmarked: BookmarkState = BookmarkState.NotBookmarked,
    ) : ScreenData()
}