package com.example.moviemania.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.example.moviemania.domain.use_case.DeleteMovieUseCase
import com.example.moviemania.domain.use_case.GetMovieDetailsUseCase
import com.example.moviemania.domain.use_case.GetSavedMoviesUseCase
import com.example.moviemania.domain.use_case.GetVideosUseCase
import com.example.moviemania.domain.use_case.SaveMovieUseCase
import com.example.moviemania.ui.common.BaseViewModel
import com.example.moviemania.ui.custom.bookmark.BookmarkState
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getVideosUseCase: GetVideosUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val saveMovieUseCase: SaveMovieUseCase,
) : BaseViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {
    private val _state = MutableStateFlow(DetailsUiState())

    override val state: StateFlow<DetailsUiState>
        get() = _state.asStateFlow()

    private val movieId = savedStateHandle[MOVIE_ID] ?: ""
    private var isBookmarked: BookmarkState = BookmarkState.NotBookmarked

    init {
        initData()
    }

    override fun handleAction(action: DetailsUiAction) {
        when (action) {
            is DetailsUiAction.OnUpdateWatchLater -> {
                safeLaunch {
                    if (action.bookmarked) {
                        deleteMovieUseCase(action.movieUiModel.toEntity())
                        isBookmarked = BookmarkState.NotBookmarked
                        newUiState(ScreenData.Data(movieUiModel = action.movieUiModel, bookmarked = isBookmarked))
                    } else {
                        saveMovieUseCase(action.movieUiModel.toEntity())
                        isBookmarked = BookmarkState.Bookmarked
                        newUiState(ScreenData.Data(movieUiModel = action.movieUiModel, bookmarked = isBookmarked))
                    }
                }
            }
        }
    }

    private fun initData() {
        safeLaunch {
            combine(
                getSavedMoviesUseCase(),
                getMovieDetailsUseCase(movieId = movieId),
                getVideosUseCase(movieId = movieId)
            ) { savedMovies, details, videos ->
                savedMovies.forEach { savedMovie ->
                    if (savedMovie.id.toString() == movieId) {
                        isBookmarked = BookmarkState.Bookmarked
                    }
                }
                if (details.data != null) {
                    val trailer = videos.data?.first {
                        (it.type == "Teaser" || it.type == "Clip") && it.site == "YouTube"
                    }
                    ScreenData.Data(movieUiModel = details.data.toUiModel(), trailer = trailer?.toUiModel(), bookmarked = isBookmarked)
                } else {
                    ScreenData.Loading
                }
            }
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.e(TAG, it.message.toString())
                    newUiState(ScreenData.Error)
                }
                .collectLatest { screenData -> newUiState(screenData) }
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }

    companion object {
        const val TAG = "MovieDetailsViewModel"
    }
}
