package com.example.moviemania.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.domain.repository.WatchLaterRepository
import com.example.moviemania.ui.MovieManiaViewModel
import com.example.moviemania.ui.composables.bookmark.BookmarkState
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MovieId
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val watchLaterRepository: WatchLaterRepository,
) : MovieManiaViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {
    private val _state = MutableStateFlow(DetailsUiState())

    override val state: StateFlow<DetailsUiState>
        get() = _state.asStateFlow()

    private val movieId = savedStateHandle[MovieId] ?: ""
    private var isBookmarked: BookmarkState = BookmarkState.NotBookmarked

    init {
        initData()
    }

    override fun handleAction(action: DetailsUiAction) {
        when (action) {
            is DetailsUiAction.OnUpdateWatchLater -> {
                viewModelScope.launch {
                    if (action.bookmarked) {
                        watchLaterRepository.deleteMovie(action.movie)
                        isBookmarked = BookmarkState.NotBookmarked
                        newUiState(ScreenData.Data(movie = action.movie, bookmarked = isBookmarked))
                    } else {
                        watchLaterRepository.saveMovie(action.movie)
                        isBookmarked = BookmarkState.Bookmarked
                        newUiState(ScreenData.Data(movie = action.movie, bookmarked = isBookmarked))
                    }
                }
            }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            combine(
                watchLaterRepository.getSavedMovies(),
                movieRepository.getMovieDetails(movieId = movieId),
                movieRepository.getVideos(movieId = movieId)
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
                    ScreenData.Data(movie = details.data, trailer = trailer, bookmarked = isBookmarked)
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
